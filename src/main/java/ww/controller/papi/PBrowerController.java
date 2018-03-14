package ww.controller.papi;

import java.util.function.UnaryOperator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ww.common.DbModel;
import ww.common.FileUploadUtil;
import ww.common.FileUploadUtil.UploadResult;
import ww.common.ModelDAO;
import ww.common.SqlList;
import ww.common.SqlMap;
import ww.common.WwLog;
import ww.common.WwSystem;
import ww.controller.BaseController;

@Controller
@RequestMapping(value="/papi/pbrower")
public class PBrowerController extends BaseController {
	
//	@Autowired
//	private UserMapper userMapper;
	
	@ResponseBody
	@RequestMapping("/appeal")
	public JSONObject appeal(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject result=new JSONObject();
		
		String realName=WwSystem.ToString(request.getParameter("realName")).trim();
		String idNum=WwSystem.ToString(request.getParameter("idNum")).trim();
		String phoneNum=WwSystem.ToString(request.getParameter("phoneNum")).trim();
		if(realName==null||StringUtils.isEmpty(realName)){
			result.put("success", false);
			result.put("code", 2);
			result.put("message", "姓名不能为空");
			return result;
		}
		/*if(idNum==null||StringUtils.isEmpty(idNum)){
			result.put("success", false);
			result.put("code", 5);
			result.put("message", "身份证不能为空");
			return result;
		}*/
		if(phoneNum==null||StringUtils.isEmpty(phoneNum)){
			result.put("success", false);
			result.put("code",6);
			result.put("message", "手机号不能为空");
			return result;
		}
		UploadResult ures1= FileUploadUtil.updateOneImage(request, "/public/file_list/appeals/", "file1");
		if(!ures1.success){
			result.put("success", false);
			result.put("code", 4);
			result.put("message", "文件上传失败！");
			WwLog.getLogger(this).error("文件上传失败:"+ures1.message);
			return result;
		}
		UploadResult ures2= FileUploadUtil.updateOneImage(request, "/public/file_list/appeals/", "file2");
		if(!ures1.success){
			result.put("success", false);
			result.put("code", 4);
			result.put("message", "文件上传失败！");
			WwLog.getLogger(this).error("文件上传失败:"+ures2.message);
			return result;
		}
		UploadResult ures3= FileUploadUtil.updateOneImage(request, "/public/file_list/appeals/", "file3");
		if(!ures1.success){
			result.put("success", false);
			result.put("code", 4);
			result.put("message", "文件上传失败！");
			WwLog.getLogger(this).error("文件上传失败:"+ures3.message);
			return result;
		}
		DbModel dao=new DbModel();
		SqlMap data=new SqlMap();
		data.put("idPhoto1", ures1.newFileName);
		data.put("idPhoto2", ures2.newFileName);
		data.put("photo", ures3.newFileName);
		data.put("realName", realName);
		data.put("idNum", idNum);
		data.put("phone", phoneNum);
		data.put("time", WwSystem.now());
		long insert = dao.table("t_reportloss_appeal").insert(data);
		
		if(insert<0){
			result.put("success", false);
			result.put("code", 3);
			result.put("message", "保存信息失败");
			return result;
		}
		result.put("success", true);
		result.put("code", 0);
		result.put("message", "成功");
		return result;
	}
	@ResponseBody
	@RequestMapping("/getReportLoss")
	public JSONObject getReportLoss(Integer pageSize,Integer pageIndex,
			HttpServletRequest request,HttpServletResponse response) throws Exception{  
		JSONObject result=new JSONObject();	
		JSONObject data=new JSONObject();
		String query=WwSystem.ToString(request.getParameter("query")).trim();//搜索条件
        query=ModelDAO.sqlParamString(query);
		if(pageSize==null)
			pageSize=20;
		if(pageIndex==null)
			pageIndex=0;
		
		DbModel  dao=new DbModel();
		//INSERT(phoneNum,3,6,'*****')     REPLACE(idNum,LEFT(idNum,length(idNum)-6),'*************')
		String sql="select count(1) as totalRows from t_reportloss r left join t_user u on r.userId=u.id where r.status=0 ";
		String sql1=" select INSERT(u.phoneNum,3,6,'*****') phoneNum,u.realName,r.id, ";
			   sql1+=" REPLACE(u.idNum,LEFT(u.idNum,length(u.idNum)-6),'*************') idNum ";
			   //sql1+=" CASE  LENGTH(u.realName) WHEN 6 THEN  REPLACE(u.realName,LEFT(u.realName,1),'***') WHEN 9 THEN  REPLACE(u.realName,LEFT(u.realName,2),'***') ELSE REPLACE(u.realName,LEFT(u.realName,length(u.realName)-6),'***') END AS realName ";
			   sql1+=" from t_reportloss r left join t_user u on r.userId=u.id ";
 	   		   sql1+=" where r.status=0 ";
		if(!query.isEmpty()){
        	query=query.replaceAll("'", ""); //防注入        	
        	sql+=" and u.phoneNum='"+query+"' or u.realName='"+query+"'";
        	sql1+=" and u.phoneNum='"+query+"' or u.realName='"+query+"'";
        }
		sql1+="  order by r.reportTime desc limit "+pageIndex+","+pageSize;
        long[] pp = dao.countPagesDirect(sql, pageSize);
        long totalRows=pp[0];//总行数
        long pageCount=pp[1];//总页数	
        //String sql1=" select * from t_reportloss order by reportTime desc limit "+pageIndex+","+pageSize;
        
        SqlList list = dao.sql(sql1).query();
       
        if(list.size()<=0){
			result.put("success", false);
			result.put("code", 1);
			result.put("message", "没数据");
			return result;
		}
		
		data.put("pageCount", pageCount);
		data.put("totalRows", totalRows);
		//data.put("pageIndex", pageIndex);
		//data.put("pageSize", pageSize);
		data.put("list",list);
		
		result.put("success", true);
		result.put("code", 0);		
		result.put("data", data);
		result.put("message", "成功");
		
		return result;
	}
	
	//搜索
	@ResponseBody
	@RequestMapping("/search")
	public JSONObject search(String key,
			HttpServletRequest request,HttpServletResponse response) throws Exception{  
		JSONObject result=new JSONObject();
		JSONObject data=new JSONObject();
		
		
		ModelDAO dao=new ModelDAO();
		
		key=ModelDAO.sqlParamString(key);
		
		if(WwSystem.isInteger(key)){	    
			String sql="select id,hash from t_block where height='"+key+"'";
			SqlList list=dao.query(sql);
			if(list.size()>0){
				data.put("type", 1);  //1-块ID 2-交易ID  3-钱包地址
				data.put("hash", list.get(0).getString("hash"));
				result.put("success", true);
				result.put("code", 0);
				result.put("data", data);
				result.put("message", "成功");
				
				return result;
			}
		}else{
			String sql="select id,hash from t_block where `hash`='"+key+"'";
			SqlList list=dao.query(sql);
			if(list.size()>0){
				data.put("type", 1);  //1-块ID 2-交易ID  3-钱包地址
				data.put("hash", list.get(0).getString("hash"));
				result.put("success", true);
				result.put("code", 0);
				result.put("data", data);
				result.put("message", "成功");
				
				return result;
			}
			
			sql="select id,txid from t_tx where txid='"+key+"'";
			list=dao.query(sql);
			if(list.size()>0){
				data.put("type", 2);  //1-块ID 2-交易ID  3-钱包地址
				data.put("hash", list.get(0).getString("txid"));
				result.put("success", true);
				result.put("code", 0);
				result.put("data", data);
				result.put("message", "成功");
				
				return result;
			}
			
			String address=getRootAddress(key,dao);
			if(address.isEmpty()){
				address=key;
			}			
			sql="select id,walletAddress,realName,phoneNum from t_user where walletAddress='"+address+"'";
			list=dao.query(sql);
			if(list.size()>0){
				data.put("type", 3);  //1-块ID 2-交易ID  3-钱包地址
				data.put("hash", list.get(0).getString("walletAddress"));
				//data.put("realName", list.get(0).getString("realName"));
				//data.put("phoneNum", list.get(0).getString("phoneNum"));
				result.put("success", true);
				result.put("code", 0);
				result.put("data", data);
				result.put("message", "成功");
				
				return result;
			}
		}		
		
		result.put("success", false);
		result.put("code", 1);
		result.put("message", "没有搜索到任何数据");
		return result;
	}
	
	//获取资产列表
	@ResponseBody
	@RequestMapping(value="/getassets")
	public JSONObject getAssets(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
				
		ModelDAO dao=new ModelDAO();
		
		StringBuffer sql=new StringBuffer();
		sql.append("select v1.id,v1.sname,v1.`name`,v1.name_en,v1.circulation from t_asset v1");

		SqlList list=dao.query(sql.toString());
		
		JSONObject data=new JSONObject();
		data.put("assets", list);	
				
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("data", data);
		
		return result;
	}
	
	//块基本信息
	@ResponseBody
	@RequestMapping("/getblockinfo")
	public JSONObject getBlockInfo(String hash,
			HttpServletRequest request,HttpServletResponse response) throws Exception{  
		JSONObject result=new JSONObject();		
		
		hash=ModelDAO.sqlParamString(hash);
		
		ModelDAO dao=new ModelDAO();
		String sql="select id,`hash`,height,time,size,version,previousblockhash,nextblockhash from t_block where `hash`='"+hash+"'";
		SqlList list=dao.query(sql);
		if(list.size()<=0){
			result.put("success", false);
			result.put("code", 1);
			result.put("message", "没数据");
			return result;
		}
		
		result.put("success", true);
		result.put("code", 0);
		result.put("data", list.get(0));
		result.put("message", "成功");
		
		return result;
	}
	
	/**
	 * 块中交易信息
	 * pageIndex:从0开始，第几页
	 * @param hash
	 * @param pageSize
	 * @param pageIndex
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getblocktxs")
	public JSONObject getBlockTxs(String hash,Integer pageSize,Integer pageIndex,
			HttpServletRequest request,HttpServletResponse response) throws Exception{  
		JSONObject result=new JSONObject();	
		JSONObject data=new JSONObject();
		
		hash=ModelDAO.sqlParamString(hash);
		
		if(pageSize==null)
			pageSize=20;
		if(pageIndex==null)
			pageIndex=0;
		
		ModelDAO dao=new ModelDAO();
		long[] pp=dao.countPagesDirect("select count(id) as totalRows from t_tx where blockhash='"+hash+"'",pageSize);
		//long totalRows=pp[0];//总行数
		long pageCount=pp[1];//总页数		
		
		String sql="select txid,time,";
            sql += " (select ifnull(sum(d.amount),0) from t_tx_detail d where way=1 and t.id=d.parentid) as amount";
            sql += " from t_tx t where blockhash='"+hash+"'";       
            
        SqlList list=dao.queryByPaging(sql, pageIndex, pageSize);
		if(list.size()<=0){
			result.put("success", false);
			result.put("code", 1);
			result.put("message", "没数据");
			return result;
		}
		
		data.put("pageCount", pageCount);
		//data.put("pageIndex", pageIndex);
		//data.put("pageSize", pageSize);
		data.put("list",list);
		
		result.put("success", true);
		result.put("code", 0);		
		result.put("data", data);
		result.put("message", "成功");
		
		return result;
	}
	
	//交易基本信息
	@ResponseBody
	@RequestMapping("/gettxinfo")
	public JSONObject getTxInfo(String txid,
			HttpServletRequest request,HttpServletResponse response) throws Exception{  
		JSONObject result=new JSONObject();	
		
		txid=ModelDAO.sqlParamString(txid);
		
		ModelDAO dao=new ModelDAO();
		String sql="select txid,time,version,blockhash,";
              sql+=" (select ifnull(sum(d.amount),0) from t_tx_detail d where way=1 and t.id=d.parentid) as amount";
              sql+=" from t_tx t where txid='"+txid+"'";
		SqlList list=dao.query(sql);
		if(list.size()<=0){
			result.put("success", false);
			result.put("code", 1);
			result.put("message", "没数据");
			return result;
		}
		
		result.put("success", true);
		result.put("code", 0);
		result.put("data", list.get(0));
		result.put("message", "成功");
		
		return result;
	}
	
	//交易明细
	@ResponseBody
	@RequestMapping("/gettxdetail")
	public JSONObject getTxDetail(String txid,
			HttpServletRequest request,HttpServletResponse response) throws Exception{  
		JSONObject result=new JSONObject();	
		JSONObject data=new JSONObject();
		
		txid=ModelDAO.sqlParamString(txid);
		
		ModelDAO dao=new ModelDAO();
		String sql="select t.address,a.`name` as assetName,a.sname as assetSName,t.amount from t_tx_detail t";
              sql+=" left join t_asset a on t.assetid=a.id";
              sql+=" where t.way=1 and txid='"+txid+"'";
		SqlList listTo=dao.query(sql);
		
		  sql="select t.address,a.`name` as assetName,a.sname as assetSName,t.amount from t_tx_detail t";
        sql+=" left join t_asset a on t.assetid=a.id";
        sql+=" where t.way=-1 and txid='"+txid+"'";
		SqlList listFrom=dao.query(sql);
		
		data.put("listFrom", listFrom);
		data.put("listTo", listTo);
		
		result.put("success", true);
		result.put("code", 0);
		result.put("data", data);
		result.put("message", "成功");
		
		return result;
	}
	
	//获取地址基本信息（持仓人信息）
	@ResponseBody
	@RequestMapping("/getaddressinfo")
	public JSONObject getAddressInfo(String address,
			HttpServletRequest request,HttpServletResponse response) throws Exception{  
		JSONObject result=new JSONObject();		
		JSONObject data=new JSONObject();
		
		address=ModelDAO.sqlParamString(address);
		
		ModelDAO dao=new ModelDAO();
		String rootAddress=getRootAddress(address,dao);
		if(rootAddress.isEmpty()){
			rootAddress=address;
		}
		String sql="select type,walletAddress,realName,phoneNum from t_user where walletAddress='"+rootAddress+"'";
		SqlList list=dao.query(sql);
		if(list.size()<=0){
			result.put("success", false);
			result.put("code", 1);
			result.put("message", "没数据");
			return result;
		}
		SqlMap sm1=list.get(0);
		data.put("walletAddress", sm1.getString("walletAddress"));
		String rname=sm1.getString("realName");
		if(rname==null||rname.isEmpty()){
			rname="";
		}else{
			if(sm1.getInt("type")!=1){
				rname="*"+rname.substring(rname.length()-1);
			}
		}
		data.put("realName", rname);
		data.put("phoneNum", sm1.getString("phoneNum"));
		
		String sql2="select count(*) as txCount from";
				sql2 +=" (select distinct txid from t_tx_detail where address='"+address+"') as w1";
		
		SqlMap sm2=dao.queryToFirst(sql2);
		
		data.put("txCount", sm2.getInt("txCount"));
		
		result.put("success", true);
		result.put("code", 0);
		result.put("data", data);
		result.put("message", "成功");
		
		return result;
	}
	
	//获取地址相关的交易
	@ResponseBody
	@RequestMapping("/getaddresstxs")
	public JSONObject getAddressTxs(String address,Integer pageSize,Integer pageIndex,
			HttpServletRequest request,HttpServletResponse response) throws Exception{  
		JSONObject result=new JSONObject();	
		JSONObject data=new JSONObject();
		
		address=ModelDAO.sqlParamString(address);
		
		if(pageSize==null)
			pageSize=20;
		if(pageIndex==null)
			pageIndex=0;
		
		ModelDAO dao=new ModelDAO();
		long[] pp=dao.countPagesDirect("select count(id) as totalRows from t_tx_detail where address='"+address+"'",pageSize);
		//long totalRows=pp[0];//总行数
		long pageCount=pp[1];//总页数		
		
		String sql="select t.txid,t.time,a.sname as assetSName, t.way*t.amount as amount from t_tx_detail t ";
            sql += " left join t_asset a on t.assetid=a.id";
            sql += " where t.rootaddress='"+address+"' or t.address='"+address+"'";   
            sql += " order by t.time desc"; 
            
        SqlList list=dao.queryByPaging(sql, pageIndex, pageSize);
		if(list.size()<=0){
			result.put("success", false);
			result.put("code", 1);
			result.put("message", "没数据");
			return result;
		}
		
		data.put("pageCount", pageCount);
		//data.put("pageIndex", pageIndex);
		//data.put("pageSize", pageSize);
		data.put("list",list);
		
		result.put("success", true);
		result.put("code", 0);		
		result.put("data", data);
		result.put("message", "成功");
		
		return result;
	}
	
	//获取资产介绍
	@ResponseBody
	@RequestMapping(value="/getassetintro")
	public JSONObject getAssetIntro(int assetId,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
				
		ModelDAO dao=new ModelDAO();
		
		StringBuffer sql=new StringBuffer();
		sql.append("select v1.id,v1.sname,v1.`name`,v1.title,v1.content from t_asset v1 where v1.id="+assetId);

		
		SqlList list=dao.query(sql.toString());
		if(list.size()<=0){
			result.put("success", false);	
			result.put("code", 1);
			result.put("message", "没有数据");	
			
			return result;
		}
		String fullUrl=WwSystem.getFullRoot(request)+"public/file_list/";
		String fcontent = (String) list.get(0).get("content");
		String replaceAll = fcontent.replaceAll("public/file_list/", fullUrl);
		list.get(0).put("content", replaceAll);
		result.put("success", true);
		result.put("code", 0);
		result.put("message", "成功");
		result.put("data", list.get(0));
		
		return result;
	}
	
	//获取发行资料
	@ResponseBody
	@RequestMapping(value="/getassetissuedata")
	public JSONObject getAssetIssueData(int assetId,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		JSONObject asset=new JSONObject();
				
		ModelDAO dao=new ModelDAO();
		
		String sql1="select v1.id,v1.sname,v1.`name`,v1.circulation from t_asset v1 where v1.id="+assetId;
		SqlList list=dao.query(sql1);
		if(list.size()<=0){
			result.put("success", false);	
			result.put("code", 1);
			result.put("message", "没有资产");			
			return result;
		}
		SqlMap smAsset=list.get(0);
		asset.put("id", smAsset.getLong("id"));
		asset.put("sname", smAsset.getString("sname"));
		asset.put("name", smAsset.getString("name"));
		asset.put("circulation", smAsset.getLong("circulation"));
		
		JSONArray issues=new JSONArray();
		asset.put("issues", issues);
		
		String sql2="select t.id,t.issueAddress,t.txid,t.signature,t.dataPackage,u.realName,t.type from t_assetIssue t";
             sql2 +=" left join t_user u on t.userId=u.id where assetId="+assetId;
		SqlList list2=dao.query(sql2);
		for(int i=0;i<list2.size();i++){
			SqlMap smIssue=list2.get(i);
			JSONObject issue=new JSONObject();
			issue.put("realName", smIssue.getString("realName"));
			issue.put("issueAddress", smIssue.getString("issueAddress"));
			issue.put("txid", smIssue.getString("txid"));
			issue.put("signature", smIssue.getString("signature"));
			issue.put("dataPackage", smIssue.getString("dataPackage"));
			issue.put("type", smIssue.getInt("type"));
			
			JSONArray files=new JSONArray();
			issue.put("files", files);
			int issueId=smIssue.getInt("id");
			String sql3="select title,size,format,name from t_assetissuefiles where issueId="+issueId;
			SqlList list3=dao.query(sql3);
			for(int n=0;n<list3.size();n++){
				SqlMap smFile=list3.get(n);
				JSONObject file=new JSONObject();
				file.put("title", smFile.getString("title"));
				file.put("size", smFile.getString("size"));
				file.put("format", smFile.getString("format"));
				file.put("name", smFile.getString("name"));
				
				files.add(file);
			}
			
			issues.add(issue);
		}
				
		result.put("success", true);
		result.put("code", 0);
		result.put("message", "成功");
		result.put("asset", asset);
		
		return result;
	}
	
	/****非接口*********************************/
	
	private String getRootAddress(String address,ModelDAO dao){
		address=ModelDAO.sqlParamString(address);
		String sql="select rootAddress from t_tx_detail where address='"+address + "' LIMIT 0,1";
		SqlMap sm=dao.queryToFirst(sql);
		if(sm==null)
			return "";
		return sm.getString("rootAddress");
	}
	
}
