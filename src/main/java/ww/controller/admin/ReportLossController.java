package ww.controller.admin;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import ww.bitcoin.WwBitcoinClient;
import ww.common.ModelDAO;
import ww.common.SqlList;
import ww.common.SqlMap;
import ww.common.WwLog;
import ww.common.WwSystem;
import ww.security.common.MD5;
import model.Admin;
import model.ReportLoss;
import ww.service.admin.ReportLossService;

@Controller
@RequestMapping(value="/admin/ReportLoss")
public class ReportLossController extends ww.controller.BaseController {
	
	@Autowired
	private ReportLossService service;
	
	@RequestMapping(value="/list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){		
		ModelAndView mv=new ModelAndView();
		
		int page=WwSystem.ToInt(request.getParameter("page"));//第几页
        int beginRow=WwSystem.ToInt(request.getParameter("beginRow"));//每页开始行
        int pageRows=WwSystem.ToInt(request.getParameter("pageRows"));//每页显示行数
        String query=WwSystem.ToString(request.getParameter("query")).trim();//搜索条件
        query=ModelDAO.sqlParamString(query);
        String status=WwSystem.ToString(request.getParameter("status"));//搜索条件
        
        if(page<=0)
            page=1;
        if(beginRow<=0)
            beginRow=0;
        if(pageRows<=0)
            pageRows=20;
        
        if(status.isEmpty())
        	status="0";
        
        String where="where status="+status;        
        
        //请根据实际搜索条件更改代码
        if(!query.isEmpty()){
        	query=query.replaceAll("'", ""); //防注入        	
        	where+=" and userId in (select id from t_user where phoneNum='"+query+"' or realName='"+query+"')";
        }
        where += " order by reportTime";
        String parkSql=where + " limit "+beginRow+","+pageRows;
    	List<ReportLoss> list=service.getList(parkSql);
    	int allRows=service.getCount(where);

    	mv.addObject("list",list); //列表数据
    	mv.addObject("page",page); //第几页
    	mv.addObject("pageRows",pageRows); //每页显示行数
    	mv.addObject("allRows",allRows); //查询总行数	
    	mv.addObject("query",query); //查询总行数	
    	mv.addObject("status",status);
		
		mv.setViewName(getListViewName());
		return mv;
	}
	
	@RequestMapping(value="/edit")
	public ModelAndView edit(java.lang.Integer id,Integer state,HttpServletRequest request,HttpServletResponse response){	
		ModelAndView mv=new ModelAndView(getEditViewName());
		ReportLoss data=null;
		if(id!=null)			
			data=service.getById(id);
		if(data==null)
			data=new ReportLoss();
		
		if(state==null)
			state=0;
		
		mv.addObject("state", state);	
		mv.addObject("data",data);		
		return mv;
	}
	
	@RequestMapping(value="/save")
	public ModelAndView save(@Valid @ModelAttribute("data") ReportLoss data,BindingResult result,
			HttpServletRequest request,HttpServletResponse response)throws Exception{		
		ModelAndView mv=null;		
		//如果有验证错误 返回到form页面
        if(result.hasErrors()){
        	mv= new ModelAndView(getEditViewName());
        	mv.addObject("data",data);
        	mv.addObject("error","验证错误！");
        	return mv;
        }
        
        ReportLoss saveData=service.getById(data.getId());
		if(saveData==null){
			saveData=new ReportLoss();
		}	
		//保存request提交的字段，其他字段为数据库已经存在的值
	    saveData.mergeProperty(data, request);	
				
		if(service.Save(saveData)){
			mv=new ModelAndView("redirect:/admin/ReportLoss/list");
		}else{			
			mv=new ModelAndView(getEditViewName());
			mv.addObject("error","保存失败！");
			mv.addObject("data",saveData);
		}
		
		return mv;
	}
	
	@RequestMapping(value="/view")
	public ModelAndView view(java.lang.Integer id,HttpServletRequest request,HttpServletResponse response){	
		ModelAndView mv=new ModelAndView(getViewViewName());
		ReportLoss data=null;
		if(id!=null)
			data=service.getById(id);
		if(data==null)
			data=new ReportLoss();
		
		mv.addObject("data",data);		
		return mv;
	}
	
	@RequestMapping(value="/delete")
	public ModelAndView delete(java.lang.Integer id,HttpServletRequest request,HttpServletResponse response){	
		ModelAndView mv=new ModelAndView("forward:/admin/ReportLoss/list");
		if(id==null){
			mv.addObject("msg","id无效！");
			mv.addObject("success","false");
			return mv;
		}
		if(service.Delete(id)){
			mv.addObject("msg","删除成功！");
			mv.addObject("success","true");
		}else{
			mv.addObject("msg","删除失败！");
			mv.addObject("success","false");
		}
		
		return mv;
	}
	
	//ajax
	@ResponseBody
	@RequestMapping(value="/check")
	public JSONObject check(java.lang.Integer id,java.lang.Integer pass,
			HttpServletRequest request,HttpServletResponse response){	
		//ModelAndView mv=new ModelAndView("forward:/admin/ReportLoss/list");
		JSONObject result=new JSONObject();
		if(id==null){
			result.put("success",false);
			result.put("msg","id无效！");			
			return result;
		}
		Admin admin=(Admin)request.getSession().getAttribute("admin");
				
		ModelDAO dao=new ModelDAO();
		String sql="update t_reportloss set status="+pass+",checkAdminId="+admin.getFid()+" , checkTime=now() where id="+id;
		try{
			dao.exec(sql);
		}catch(Exception e){
			result.put("success",false);
			result.put("msg","更新t_reportloss表异常："+e.getMessage());			
			return result;
		}
		
		result.put("success",true);
		result.put("msg","审核成功");			
		return result;
	}
	
	//ajax
	@ResponseBody
	@RequestMapping(value="/doloss")
	public JSONObject doLoss(java.lang.Integer id,java.lang.String password,
			HttpServletRequest request,HttpServletResponse response){	
		//ModelAndView mv=new ModelAndView("forward:/admin/ReportLoss/list");
		JSONObject result=new JSONObject();
		if(id==null){
			result.put("success",false);
			result.put("msg","id无效！");			
			return result;
		}
		Admin admin=(Admin)request.getSession().getAttribute("admin");
	    //转账密码验证**************************
		if(admin==null){
			result.put("success",false);
			result.put("msg","请登录！");			
			return result;
		}
		if (password==null) {
			result.put("success",false);
			result.put("msg","请输入登录密码！");			
			return result;
		}
		if (admin.getOkPassword()==null) {
			result.put("success",false);
			result.put("msg","请在系统设置中设置转账密码！");			
			return result;
		}
		if (!admin.getOkPassword().equals(MD5.md5(password))) {
			result.put("success",false);
			result.put("msg","输入的转账密码不正确！");			
			return result;
		}
		
		ModelDAO dao=new ModelDAO();		
		SqlMap reportloss=dao.M("t_reportloss").where("id="+id).selectOne();
		if(reportloss==null){
			result.put("success",false);
			result.put("msg","id="+id+"挂失申请不存在。");			
			return result;
		}
		
		String fromWalletAddress=reportloss.getString("walletAddressOld");
		if(fromWalletAddress==null||fromWalletAddress.isEmpty()||fromWalletAddress.trim().equals("")){
			result.put("success",false);
			result.put("msg","id="+id+"的挂失记录里没有老钱包地址。");			
			return result;
		}
		
		long userId=reportloss.getLong("userId");
		if(userId<=0){
			result.put("success",false);
			result.put("msg","挂失记录中的userId无效！");			
			return result;
		}
		
		SqlMap user=dao.M("t_user").where("id="+userId).selectOne();
		if(user==null){
			result.put("success",false);
			result.put("msg","id="+userId+"会员用户不存在。");			
			return result;
		}
		
		String toWalletAddress=user.getString("walletAddress");
		if(toWalletAddress==null||toWalletAddress.isEmpty()||toWalletAddress.trim().equals("")){
			result.put("success",false);
			result.put("msg","id="+userId+"会员用户没注册钱包地址。");			
			return result;
		}
		
		//执行找回
		JSONObject res=doLoss(fromWalletAddress,toWalletAddress,userId);
		if(res.getBoolean("success")){		
			//更新状态
			String msggg=res.getString("msg");
			msggg=msggg.replaceAll("'", "");
			msggg=msggg.replaceAll("\r", "");
			msggg=msggg.replaceAll("\n", "");
			String sql="update t_reportloss set status=2 , lossTime=now() , lossAdminId="+admin.getFid()+" , toWalletAddress='"+toWalletAddress+"' , lossError='"+msggg+"' where id="+id;
			try{
				dao.exec(sql);
			}catch(Exception e){
				result.put("success",false);
				result.put("msg","更新t_reportloss表异常："+e.getMessage());			
				return result;
			}			
			result.put("success",true);
			result.put("msg","找回成功");			
			return result;		
		}else{
			//更新状态
			String error=res.getString("msg");
			error=error.replaceAll("'", "");
			error=error.replaceAll("\r", "");
			error=error.replaceAll("\n", "");
			String sql="update t_reportloss set status=-2 , lossTime=now() , lossAdminId="+admin.getFid()+" , toWalletAddress='"+toWalletAddress+"' , lossError='"+error+"' where id="+id;
			try{
				dao.exec(sql);
			}catch(Exception e){
				result.put("success",false);
				result.put("msg","更新t_reportloss表异常："+e.getMessage());			
				return result;
			}		
			return res;
		
		}
	}
	
	//ajax查看老地址的余额
	@ResponseBody
	@RequestMapping(value="/getOldBalance")
	public JSONObject getOldBalance(java.lang.Integer id,
			HttpServletRequest request,HttpServletResponse response){	
		//ModelAndView mv=new ModelAndView("forward:/admin/ReportLoss/list");
		JSONObject result=new JSONObject();
		if(id==null){
			result.put("success",false);
			result.put("msg","id无效！");			
			return result;
		}
				
		ModelDAO dao=new ModelDAO();
		SqlMap reportloss=dao.M("t_reportloss").where("id="+id).selectOne();
		if(reportloss==null){
			result.put("success",false);
			result.put("msg","id="+id+"挂失申请不存在。");			
			return result;
		}
		
		String fromWalletAddress=reportloss.getString("walletAddressOld");
		if(fromWalletAddress==null||fromWalletAddress.isEmpty()||fromWalletAddress.trim().equals("")){
			result.put("success",false);
			result.put("msg","id="+id+"的挂失记录里没有老钱包地址。");			
			return result;
		}
		
		SqlList list=dao.query("call p_getbalancebyaddr2('"+fromWalletAddress+"')");
		if(list.size()<=0){
			result.put("success",true);
			result.put("msg","老钱包地址("+fromWalletAddress+")没有资产!");			
			return result;
		}else{
			result.put("success",true);
			result.put("msg","老钱包地址("+fromWalletAddress+")的资产："+list.toJSONArray().toString()+"");			
			return result;
		}
	}
	
	//获取新地址的余额
	@ResponseBody
	@RequestMapping(value="/getNewBalance")
	public JSONObject getNewBalance(java.lang.Integer id,
			HttpServletRequest request,HttpServletResponse response){	
		//ModelAndView mv=new ModelAndView("forward:/admin/ReportLoss/list");
		JSONObject result=new JSONObject();
		if(id==null){
			result.put("success",false);
			result.put("msg","id无效！");			
			return result;
		}
				
		ModelDAO dao=new ModelDAO();
		SqlMap reportloss=dao.M("t_reportloss").where("id="+id).selectOne();
		if(reportloss==null){
			result.put("success",false);
			result.put("msg","id="+id+"挂失申请不存在。");			
			return result;
		}
		
		String toWalletAddress=reportloss.getString("walletAddressNew");
		if(toWalletAddress==null||toWalletAddress.isEmpty()||toWalletAddress.trim().equals("")){
			result.put("success",false);
			result.put("msg","id="+id+"的挂失记录里没有新钱包地址。");			
			return result;
		}
		
		SqlList list=dao.query("call p_getbalancebyaddr2('"+toWalletAddress+"')");
		if(list.size()<=0){
			result.put("success",true);
			result.put("msg","新钱包地址("+toWalletAddress+")没有资产!");			
			return result;
		}else{
			result.put("success",true);
			result.put("msg","新钱包地址("+toWalletAddress+")的资产："+list.toJSONArray().toString()+"");			
			return result;
		}
	}
	
	private String getEditViewName(){
		return "admin/ReportLossEdit";
	}
	
	private String getViewViewName(){
		return "admin/ReportLossView";
	}
	
	private String getListViewName(){
		return "admin/ReportLossList";
	}
	
	/***********非接口*******************************/
	private JSONObject doLoss(String oldAddress,String newAddress,long userId){
		JSONObject result=new JSONObject();
		
//		//test
//		if(1==1){
//			result.put("success", true);
//			result.put("msg", "新地址不能为空");
//			return result;
//		}
		
		if(newAddress==null||newAddress.isEmpty()){
			result.put("success", false);
			result.put("code", 2);
			result.put("msg", "新地址不能为空");
			return result;
		}	

		if(oldAddress==null || oldAddress.isEmpty()){
			result.put("success", false);
			result.put("code", 3);
			result.put("msg", "老地址不能为空");
			return result;
		}	
		
		if(userId<=0){
			result.put("success", false);
			result.put("code", 4);
			result.put("msg", "无效的UserId");
			return result;
		}
		
		WwBitcoinClient client=WwBitcoinClient.instMainNode();		
		String signprogram="/home/yyw/projects/scripts/opsigner.py";
		String wizardprogram="/home/yyw/projects/scripts/wizard.py";		
		
		ModelDAO dao=new ModelDAO();
		SqlList list=dao.query("call p_listunspent3('"+oldAddress+"')");
		if(list.size()<=0){
			result.put("success", true);
			result.put("code", 0);
			result.put("msg", "此地址没有未消费的记录(没有资产)，无需找回,属于挂失成功。");
			return result;
		}
		
		//指派wizardid到账本
//		String jsonstr0=client.getWizardidFromRemote();			
//		JSONObject resJson0=JSONObject.parseObject(jsonstr0);
//		if(!resJson0.getBoolean("success")){
//			result.put("success", false);
//			result.put("code", 6);
//			result.put("msg", "调用wizard.py获取wizardid失败："+resJson0.getString("msg"));
//			WwLog.getLogger(this).error("调用wizard.py获取wizardid失败："+resJson0.getString("msg"));
//			return result;
//		}		
//		String wizardid11=resJson0.getString("wizardid");
		
//		String jsonstr11=client.assignWizardid(signprogram, wizardid11);
//		JSONObject resJson11=JSONObject.parseObject(jsonstr11);
//		if(!resJson11.getBoolean("success")){
//			result.put("success", false);
//			result.put("code", 7);
//			result.put("wizardid", wizardid11);
//			result.put("msg", "调用operatoraction失败："+resJson11.getString("msg"));
//			WwLog.getLogger(this).error("调用operatoraction失败："+resJson11.getString("msg"));
//			return result;
//		}
		
		//将老地址资产转移到新地址
		//未消费记录,格式："地址-vout","地址-vout",...
		String unspentlist="";
		for(int i=0;i<list.size();i++){
			if(!unspentlist.isEmpty())
				unspentlist+=",";
			unspentlist+="\""+list.get(i).getString("txid")+"-"+list.get(i).getInt("out_index")+"\"";
		}		
		String jsonstr3=client.wizardTransfer(wizardprogram, unspentlist, newAddress);
		JSONObject resJson3=JSONObject.parseObject(jsonstr3);
		if(!resJson3.getBoolean("success")){
			result.put("success", false);
			result.put("code", 8);
			result.put("unspentlist", unspentlist);
			result.put("msg", "调用wizardtransfer失败："+resJson3.getString("msg"));
			WwLog.getLogger(this).error("调用wizardtransfer失败："+resJson3.getString("msg"));
			return result;
		}
		String wizardTxId=resJson3.getString("txid");
		
		//指派wizardid到账本
//		String jsonstr4=client.getWizardidFromRemote();			
//		JSONObject resJson4=JSONObject.parseObject(jsonstr4);
//		if(!resJson4.getBoolean("success")){
//			result.put("success", false);
//			result.put("code", 9);
//			result.put("msg", "调用wizard.py获取wizardid失败："+resJson4.getString("msg"));
//			WwLog.getLogger(this).error("调用wizard.py获取wizardid失败："+resJson4.getString("msg"));
//			return result;
//		}		
//		String wizardid=resJson4.getString("wizardid");		
//		String jsonstr5=client.assignWizardid(signprogram, wizardid);
//		JSONObject resJson5=JSONObject.parseObject(jsonstr5);
//		if(!resJson5.getBoolean("success")){
//			result.put("success", false);
//			result.put("code", 10);
//			result.put("wizardid", wizardid);
//			result.put("msg", "调用operatoraction失败："+resJson5.getString("msg"));
//			WwLog.getLogger(this).error("调用operatoraction失败："+resJson5.getString("msg"));
//			return result;
//		}
		
		//更新挂失记录状态
		try{
			dao.exec("update t_reportloss set status=2 where walletAddressOld='"+oldAddress+"' and userId="+userId);
		}catch(Exception e){
			result.put("success", false);
			result.put("code", 11);
			result.put("msg", "更新挂失记录状态(userId="+userId+" oldAddress="+oldAddress+")异常："+e.getMessage());
			WwLog.getLogger(this).error("更新挂失记录状态(userId="+userId+" oldAddress="+oldAddress+")异常："+e.getMessage());
			return result;
		}
		
		result.put("msg", "挂失成功");		
		result.put("success", true);	
		result.put("code", 0);
		result.put("wizardTxId", wizardTxId);
		return result;
	}
	
}
