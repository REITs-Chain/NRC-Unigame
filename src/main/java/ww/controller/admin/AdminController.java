package ww.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import model.Admin;
import model.User;
import model.rongCloud.CodeSuccessResult;
import ww.authen.ApiLoginContext;
import ww.authen.ApiLoginContext.PhoneValidCodeInfo;
import ww.common.DbModel;
import ww.common.Md5Encrypt;
import ww.common.SmsUtil_heng;
import ww.common.SqlList;
import ww.common.SqlMap;
import ww.common.WwSystem;
import ww.rong.RongCloudUtil;
import ww.security.common.MD5;
import ww.service.admin.AdminService;

@Controller
@RequestMapping(value="/admin/Admin")
public class AdminController extends ww.controller.BaseController {
	
	@Autowired
	private AdminService service;
	
	//给群中添加用户
	@ResponseBody
	@RequestMapping(value="/addUserToGroup")
	public ModelAndView addUserToGroup(Integer groupId,Integer minStarLevel,String[] groupJoinUserId,String groupName,
			HttpServletRequest request,
			HttpServletResponse response){
		
		ModelAndView mv=new ModelAndView();
		
		HttpSession session = request.getSession();
		Admin u = (Admin) session.getAttribute("admin");
		if (u==null) {
			mv.addObject("success", false);
			mv.addObject("code",1);
			mv.addObject("message","请登录！");
			return mv;
		} 
		
		String gid = String.valueOf(groupId);
		CodeSuccessResult join=null;
		try {
			join = RongCloudUtil.join(groupJoinUserId, gid, groupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//确认创建群组
	@ResponseBody
	@RequestMapping(value="/createGroup")
	public ModelAndView createGroup(Integer groupId,Integer minStarLevel,Integer userId,String createPerson,String groupName,
			HttpServletRequest request,
			HttpServletResponse response){
		
		ModelAndView mv=new ModelAndView();
		
		HttpSession session = request.getSession();
		Admin u = (Admin) session.getAttribute("admin");
		if (u==null) {
			mv.addObject("success", false);
			mv.addObject("code",1);
			mv.addObject("message","请登录！");
			return mv;
		} 
		DbModel dao=new DbModel();
		SqlMap data=new SqlMap();
		data.put("status", 0);//0-已审核
		data.put("synchron", 0);//0-已同步
		int i = dao.table("t_group")
				   .where("id=:id")
				   .bind("id", userId)
				   .update(data);
		if (i<0) {
			mv.addObject("success", false);
			mv.addObject("code",2);
			mv.addObject("message","状态修改失败");
			return mv;
		} 
		data.clear();
		//根据创建人查询其ID
		SqlMap selectOne = dao.table("t_user").where("phoneNum=:phoneNum").bind("phoneNum",createPerson).selectOne();
		int userid = selectOne.getInt("id");
		//添加关联表信息
		data.put("userId", userid);
		data.put("groupId", groupId);
		long l = dao.table("t_user_group").insert(data);
		if (l<0) {
			mv.addObject("success", false);
			mv.addObject("code",3);
			mv.addObject("message","关联表添加失败");
			return mv;
		} 
		//在融云创建群
		String uid = String.valueOf(userid);
		String[] groupCreateUserId = {uid};
		String gid=String.valueOf(groupId);
		CodeSuccessResult create=null;
		try {
			 create= RongCloudUtil.create(groupCreateUserId, gid,groupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (create==null) {
			mv.addObject("success", false);
			mv.addObject("code",4);
			mv.addObject("message","在云端创建失败");
			return mv;
		}
		mv.addObject("success", true);
		mv.addObject("code",0);
		mv.addObject("message","创建成功");
		return mv;
	}

	//跳转到获取手机验证
	@RequestMapping(value="gotoOkPassword")
	public ModelAndView gotoOkPassword(HttpServletRequest request,
			HttpServletResponse response){
		ModelAndView mv=new ModelAndView();
		Admin admin=(Admin)request.getSession().getAttribute("admin");
		
		mv.addObject("phoneNum", admin.getPhoneNum());
		mv.setViewName("admin/getAdminValidNum");		
		return mv;
	}
	//获取手机验证
	@ResponseBody
	@RequestMapping(value="/getAdminValidNum")
	public ModelAndView getAdminValidNum(
			HttpServletRequest request,
			HttpServletResponse response){
		
		ModelAndView mv=new ModelAndView();
		
		HttpSession session = request.getSession();
		Admin u = (Admin) session.getAttribute("admin");
		if (u==null) {
			mv.addObject("success", false);
			mv.addObject("code",2);
			mv.addObject("message","请登录！");
			return mv;
		} 
		//获取验证码
		String verifyNum = SmsUtil_heng.getNewVerifyNum();
		//将验证码存入session中
		Admin admin=new Admin();
		admin.setVerifyNum(verifyNum);
		admin.setPhoneNum(u.getPhoneNum());
		session.setAttribute("verify", admin);
		//获取用户手机号码
		String fname = u.getFname();
		List<Admin> list= service.getByFname(fname);
		String phoneNum = list.get(0).getPhoneNum();
		//发送验证码
		SmsUtil_heng.sendVerifyNum_ChangePW(phoneNum, verifyNum);
		mv.addObject("success",true);
		mv.addObject("code",0);
		mv.addObject("message","验证码已发送。");
		mv.setViewName("admin/AdminUpdateOkPwd");
		return mv;
	}
	
	//修改确认执行的密码
	@RequestMapping(value="/updateOkPwd")
	public  ModelAndView updateOkPwd(
			java.lang.String okPassword,
			java.lang.String verifyNum,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		ModelAndView mv=new ModelAndView();		
		HttpSession session = request.getSession();
		if (null!=okPassword) {
			 //从session中获取当前登陆的用户
			 Admin ad = (Admin) session.getAttribute("verify");
		
			if (null != ad && ad.getVerifyNum().equals(verifyNum)) {
				Admin a=new Admin();
				a.setPhoneNum(ad.getPhoneNum());
				a.setOkPassword(MD5.md5(okPassword));
				service.updateOkPwd(a);
				session.invalidate();
				mv.setViewName("login/login");
			}else{
				mv.addObject("error", "密码修改失败！");
				mv.setViewName(getListViewName());
			}
		}else{
			mv.setViewName(getUpdatePwd());
		}
		return mv;
	}


	
	
	//根据username修改密码
	@RequestMapping(value="/updatePwd")
	public  ModelAndView updatePwd(java.lang.String fpassword,java.lang.String password,HttpServletRequest request,HttpServletResponse response)throws Exception{
		ModelAndView mv=new ModelAndView();		
		HttpSession session = request.getSession();
		if (null!=fpassword) {
			 //从session中获取当前登陆的用户
			 Admin ad = (Admin) session.getAttribute("admin");
		
			if (null != ad && ad.getFpassword().equals(MD5.md5(fpassword))) {
				Admin a=new Admin();
				a.setFname(ad.getFname());
				a.setFpassword(MD5.md5(password));
				service.updatePwd(a);
				session.invalidate();
				mv.setViewName("login/login");
			}else{
				mv.addObject("error", "密码修改失败！");
				mv.setViewName(getListViewName());
			}
		}else{
			mv.setViewName(getUpdatePwd());
		}
		return mv;
	}
	
	
	@RequestMapping(value="/list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response)throws Exception{		
		ModelAndView mv=new ModelAndView();	
		
		//test
//		WwSessionCache.getInstance().setString("ww12345", "ww is ok");		
//		String ss=WwSessionCache.getInstance().getString("ww12345");		
//		String s1=ss;
//		String s2=s1;		
//		String ss2=WwSessionCache.getInstance().getString("ww12345");
		
		
		
		int page=WwSystem.ToInt(request.getParameter("page"));//第几页
        int beginRow=WwSystem.ToInt(request.getParameter("beginRow"));//每页开始行
        int pageRows=WwSystem.ToInt(request.getParameter("pageRows"));//每页显示行数
        String query=WwSystem.ToString(request.getParameter("query"));//搜索条件
        
        if(page<=0)
            page=1;
        if(beginRow<=0)
            beginRow=0;
        if(pageRows<=0)
            pageRows=20;
        
        String where="";
        /*
        //请根据实际搜索条件更改代码
        if(!query.isEmpty()){
        	where=" where fname='"+query+"'";
        }*/

        String parkSql=where + " limit "+beginRow+","+pageRows;
    	List<Admin> list=service.getList(parkSql);
    	int allRows=service.getCount(where);

    	mv.addObject("list",list); //列表数据
    	mv.addObject("page",page); //第几页
    	mv.addObject("pageRows",pageRows); //每页显示行数
    	mv.addObject("allRows",allRows); //查询总行数	
    	mv.addObject("query",query); //查询总行数		
		
		mv.setViewName(getListViewName());
		return mv;
	}
	
	@RequestMapping(value="/edit")
	public ModelAndView edit(java.lang.Integer id,Integer state,HttpServletRequest request,HttpServletResponse response)throws Exception{	
		ModelAndView mv=new ModelAndView(getEditViewName());
		Admin data=null;
		if(id!=null)			
			data=service.getById(id);
		if(data==null)
			data=new Admin();
		
		if(state==null)
			state=0;
		
		mv.addObject("state", state);	
		mv.addObject("data",data);		
		return mv;
	}
	
	@RequestMapping(value="/save")
	public ModelAndView save(@Valid @ModelAttribute("data") Admin data,BindingResult result,
			HttpServletRequest request,HttpServletResponse response)throws Exception{		
		ModelAndView mv=null;		
		//如果有验证错误 返回到form页面
        if(result.hasErrors()){
        	mv= new ModelAndView(getEditViewName());
        	mv.addObject("data",data);
        	mv.addObject("error","验证错误！");
        	return mv;
        }
        
        Admin saveData=service.getById(data.getFid());
		if(saveData==null){
			saveData=new Admin();
		}	
		//保存request提交的字段，其他字段为数据库已经存在的值
	    saveData.mergeProperty(data, request);	
				
		if(service.Save(saveData)){
			mv=new ModelAndView("redirect:/admin/Admin/list");
		}else{			
			mv=new ModelAndView(getEditViewName());
			mv.addObject("error","保存失败！");
			mv.addObject("data",saveData);
		}
		
		return mv;
	}
	
	@RequestMapping(value="/delete")
	public ModelAndView delete(java.lang.Integer id,HttpServletRequest request,HttpServletResponse response)throws Exception{	
		ModelAndView mv=new ModelAndView("forward:/admin/Admin/list");
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
	
	@ResponseBody
	@RequestMapping(value="/sendNoticeMsg")
	public JSONObject sendNoticeMsg(
			HttpServletRequest request,HttpServletResponse response)throws Exception{	
		JSONObject result=new JSONObject();
		
		String notice="各位尊敬的瑞资链支持者：非常感谢各位一直以来对瑞资链项目团队的支持与包容，目前瑞资项目团队进行ICO365一级市场的清退工作。9月9日——9月14日之间，需要投资者通过发送邮件到bd@reitsfax.com，写明姓名、身份证号、联系手机、邮箱、RTS数量、ICO365参与该项目的纪录截图，注明OKCoin或者火币接收BTC或ETH的地址，缺一不可。9月15日开始针对这部分用户进行发币工作。（注意不要出错误，否则瑞资链项目团队概不负责）";

		DbModel db=new DbModel();
		int errors=0;
		int oks=0;
		SqlList list=db.table("t_sms_notice").where("ifnull(isSend,0)!=1").select();
		for(int i=0;i<list.size();i++){
			SqlMap item=list.get(i);
			int id=item.getInt("id");
			String phoneNum=item.getString("phoneNum");
			if(SmsUtil_heng.sendNotice(phoneNum,notice)){				
				oks++;
				db.table("t_sms_notice").where("id=:id")
					.bind("id", id)
					.update("isSend=1");
			}else{
				errors++;
				db.table("t_sms_notice").where("id=:id")
				.bind("id", id)
				.bind("note", SmsUtil_heng.message)
				.update("isSend=0,note=:note");
			}
		}
		
		result.put("success", true);
		result.put("code", 0);
		result.put("message", "发送完成，共"+oks+"个发送成功，有个"+errors+"发送失败。");
		return result;
	}
	
	private String getEditViewName(){
		return "admin/AdminEdit";
	}
	
	private String getListViewName(){
		return "admin/AdminList";
	}
	
	private String getUpdatePwd(){
		return "admin/AdminUpdatePwd";
	}
	
}
