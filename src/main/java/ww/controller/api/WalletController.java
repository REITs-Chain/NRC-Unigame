package ww.controller.api;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import model.Linker;
import model.User;
import ww.authen.ApiLoginContext;
import ww.authen.ApiLoginContext.PhoneValidCodeInfo;
import ww.authen.LoginUser;
import ww.bitcoin.Config;
import ww.bitcoin.WwBitcoinClient;
import ww.common.Md5Encrypt;
import ww.common.ModelDAO;
import ww.common.SqlList;
import ww.common.SqlMap;
import ww.common.WwLog;
import ww.common.WwSystem;
import ww.controller.BaseController;
import ww.dao.LinkerMapper;

/**
 * 钱包专用接口
 * @author ww
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)  //跨域post请求
@Controller
@RequestMapping(value="/api/wallet")
public class WalletController extends BaseController {
	
	@Autowired
	private LinkerMapper linkerMapper;

	public WalletController() {
		// TODO Auto-generated constructor stub
	}
	
	//获取我的联系人
	@ResponseBody
	@RequestMapping(value="/getlinkers",method=RequestMethod.POST)
	public JSONObject getLinkers(String token,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		//ModelDAO dao=new ModelDAO();
		//SqlList list=dao.M("t_walletaddress").where("userId='"+luser.getUserid()+"'").select("id,address");
		
		List<Linker> list=linkerMapper.getByUserId(luser.getUserid());
		
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("data", list);
		return result;
	}
	
	//添加新联系人
	@ResponseBody
	@RequestMapping(value="/addlinker",method=RequestMethod.POST)
	public JSONObject addLinker(String token,Linker linker,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		//ModelDAO dao=new ModelDAO();
		//SqlList list=dao.M("t_walletaddress").where("userId='"+luser.getUserid()+"'").select("id,address");
		
		linker.setUserId(luser.getUserid());
		int res=1;
		if(linker.getId()==""||linker.getId()==null){
			linker.setId(WwSystem.UUID());
			res=linkerMapper.insert(linker);
		}else{
			try{
				linkerMapper.update(linker);
			}catch(Exception e){
				result.put("success", false);
				result.put("code", 2);
				result.put("message", "保存数据库失败："+e.getMessage());
				return result;
			}
		}
		if(res<=0){
			result.put("success", false);
			result.put("code", 2);
			result.put("message", "插入数据库失败1");
			return result;
		}
				
		result.put("success", true);	
		result.put("code", 0);
		result.put("id", linker.getId());
		result.put("message", "成功");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/deletelinker",method=RequestMethod.POST)
	public JSONObject deleteLinker(String token,String linkerId,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		//ModelDAO dao=new ModelDAO();
		//SqlList list=dao.M("t_walletaddress").where("userId='"+luser.getUserid()+"'").select("id,address");

		linkerMapper.delete(linkerId);
				
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/deletelinker2",method=RequestMethod.POST)
	public JSONObject deleteLinker2(String token,String linkerAddress,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		dao.M("t_linker").where("linkerAddress='"+linkerAddress+"'").Delete();
				
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");
		return result;
	}
	
	//获取我的钱包地址
	@ResponseBody
	@RequestMapping(value="/getwalletaddresses")
	public JSONObject getWalletAddresses(String token,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		SqlMap sm=dao.M("t_user").where("id="+luser.getUserid()).selectOne("walletAddress");
		if(sm==null){
			result.put("success", false);
			result.put("code", 2);
			result.put("message", "没有找到用户");
			return result;
		}
		
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("walletAddress", sm.getString("walletAddress"));
		return result;
	}
	
	//获取我的钱包地址
	@ResponseBody
	@RequestMapping(value="/getuserbyaddress",method=RequestMethod.POST)
	public JSONObject getUserByAddresses(String token,String address,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			result.put("userId", 0);
			result.put("phoneNum", "");
			result.put("realName", "");
			result.put("nickName", "");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		SqlMap sm=dao.M("t_user").where("walletAddress='"+address+"'").selectOne();
		if(sm==null){
			result.put("success", false);
			result.put("code", 2);
			result.put("message", "找不到对应的用户");
			result.put("userId", 0);
			result.put("phoneNum", "");
			result.put("realName", "");
			result.put("nickName", "");
			return result;
		}
		
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("userId", sm.getLong("id"));
		result.put("phoneNum", sm.getString("phoneNum"));
		result.put("realName", sm.getString("realName"));
		result.put("nickName", sm.getString("nickName"));
		
		return result;
	}
	
	//获取发布资产列表
	@ResponseBody
	@RequestMapping(value="/getassets")
	public JSONObject getAssets(String token,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		
		SqlList list=dao.M("t_asset").select("id,sname,name,name_en,circulation,imageUrl,produtIntroUrl,issueDatum");
				
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("data", list);
		
		
		return result;
	}
	
	//获取发布资产和我的持有量	
	@ResponseBody
	@RequestMapping(value="/getassetsandhold")
	public JSONObject getAssetsAndHold(String token,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		
		String lastsql="select year(FROM_UNIXTIME(max(time))) as lastyear,month(FROM_UNIXTIME(max(time))) as lastmonth,day(FROM_UNIXTIME(max(time))) as lasyday from t_addr_balance";
		SqlMap last_sm=dao.queryToFirst(lastsql);
		Date now=new Date();
		int lastYear=WwSystem.getYear(now);
		int lastMonth=WwSystem.getYear(now);
		int lastDay=WwSystem.getYear(now);
		if(last_sm!=null){
			lastYear=last_sm.getInt("lastyear");
			lastMonth=last_sm.getInt("lastmonth");
			lastDay=last_sm.getInt("lasyday");
		}
		
		StringBuffer sql=new StringBuffer();
		sql.append("select v1.id,v1.`name`,v1.name_en,v1.circulation, ifnull(w1.balance,0) as balance from t_asset v1");
		sql.append(" left join(");
		sql.append(" select t1.assetid,sum(t1.balance) as balance ");
		sql.append(" from t_addr_balance t1");
		sql.append(" where t1.`year`="+lastYear);
		sql.append("       and t1.`month`="+lastMonth);
		sql.append("       and t1.`day`="+lastDay);
		sql.append("       and t1.userid="+luser.getUserid());
		sql.append(" group by t1.assetid");
		sql.append(" )w1 on v1.id=w1.assetid");
		
		//SqlList list=dao.M("t_asset").select("id,name,name_en,circulation");
		SqlList list=dao.query(sql.toString());
				
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("data", list);
		
		
		return result;
	}
	
//	//获取交易记录
//	@ResponseBody
//	@RequestMapping(value="/gettxrecords")
//	public JSONObject getTxRecords(String token,Integer pageSize,Integer pageIndex,
//			HttpServletRequest request,HttpServletResponse response){
//		JSONObject result=new JSONObject();
//		
//		LoginUser luser=ApiLoginContext.getUser(token);
//		if(luser==null){
//			result.put("success", false);
//			result.put("code", 101);
//			result.put("message", "无效token");
//			return result;
//		}
//		
//		if(pageSize==null)
//			pageSize=100;
//		if(pageIndex==null)
//			pageIndex=0;
//		
//		ModelDAO dao=new ModelDAO();
//        
////        String sql="select txId,'"+luser.getRealName()+"' as userName,time,rootAddress,address,assetId,assetName,IF(qty<0,-1,1) as way,qty from (";
////        sql+=" select t.txId,max(time) as time,t.rootAddress,t.address,t.assetId, a.`name` as assetName,sum(t.way*t.amount) as qty from t_tx_detail t";
////        sql+=" inner join t_asset a on t.assetid=a.id";
////        sql+=" where t.userId="+luser.getUserid();
////        sql+=" group by t.txid,t.rootAddress,t.assetid";
////        sql+=" )w1";
////        sql+=" where w1.qty<>0";
////        sql+=" order by time desc";
//		
//		String sql="select t.txId,";
//		sql+=" IF(t.from_userId="+luser.getUserid()+",u2.realName,u1.realName) as userName,t.time,";		
//		sql+=" IF(t.from_userId="+luser.getUserid()+",t.to_rootAddress,t.from_rootAddress) as rootAddress,";
//		sql+=" t.to_address as address,";		
//		sql+=" t.assetId,a.name as assetName,";
//		sql+=" IF(t.from_userId="+luser.getUserid()+",-1,1) as way,";
//		sql+=" IF(t.from_userId="+luser.getUserid()+",-1*t.amount,t.amount) as qty";
//		sql+=" from t_tx_record t";
//		sql+=" left join t_user u1 on t.from_userId=u1.id";
//		sql+=" left join t_user u2 on t.to_userId=u2.id";
//		sql+=" left join t_asset a on t.assetid=a.id";
//		sql+=" where from_userId="+luser.getUserid()+" or to_userId="+luser.getUserid();
//		sql+=" order by time desc ";
//        
//        
//		SqlList list=dao.queryByPaging(sql, pageIndex, pageSize);
//				
//		result.put("success", true);	
//		result.put("code", 0);
//		result.put("message", "成功");	
//		result.put("data", list);
//		return result;
//	}
	
	//获取资产账本
	@ResponseBody
	@RequestMapping(value="/getassetaccbook")
	public JSONObject getAssetAccBook(String token,Integer assetId,Integer pageSize,Integer pageIndex,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		if(pageSize==null)
			pageSize=100;
		if(pageIndex==null)
			pageIndex=0;
		
		ModelDAO dao=new ModelDAO();
        
//		String sql="select t.txId,t.time,";
//        sql+=" IF(t.from_userId='"+luser.getUserid()+"',u2.realName,u1.realName) as userName,";
//        sql+=" IF(t.from_userId='"+luser.getUserid()+"',t.rootAddress,t.from_rootAddress) as rootAddress,";
//        sql+=" IF(t.from_userId='"+luser.getUserid()+"',-1,1) as way,";
//        sql+=" t.assetId,";
//        sql+=" IF(t.from_userId='"+luser.getUserid()+"',-1*t.amount,t.amount) as qty";
//        sql+=" from t_tx_detail t";
//        sql+=" left join t_user u1 on t.from_rootAddress=u1.walletAddress";
//        sql+=" left join t_user u2 on t.rootAddress=u2.walletAddress";
//        sql+=" where t.way=1 and t.assetId=" + assetId;
//        sql+=" 	     and t.from_rootAddress!=t.rootAddress";
//        sql+="       and (t.from_userId='"+luser.getUserid()+"' or t.userId='"+luser.getUserid()+"')";
//        sql+=" order by time desc"; 
        
        
        String sql="select w.txId,w.time,u.realName as userName,w.rootAddress,w.way,w.assetId,w.way*w.amount as qty";
        sql+=" from(";
        sql+="   select t.txId,t.time,";
        sql+=" 	 IF(t.from_userId='"+luser.getUserid()+"',t.rootAddress,t.from_rootAddress) as rootAddress,";
        sql+=" 	 IF(t.from_userId='"+luser.getUserid()+"',-1,1) as way,";
        sql+=" 	 t.assetId,";
        sql+=" 	 t.amount";
        sql+=" 	 from t_tx_detail t";
        sql+=" 	 where t.way=1 and t.assetId="+assetId;
        sql+=" 				 and t.from_rootAddress!=t.rootAddress";
        sql+=" 				 and (t.from_userId='"+luser.getUserid()+"' or t.userId='"+luser.getUserid()+"')";         
        sql+=" )w";
        sql+=" left join t_user u on w.rootAddress=u.walletAddress";
        sql+=" order by time desc";
        
		SqlList list=dao.queryByPaging(sql, pageIndex, pageSize);
				
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("data", list);
		return result;
	}
	
	//获取资产账本
	@ResponseBody
	@RequestMapping(value="/getassetaccbookbyaddr")
	public JSONObject getAssetAccBookByAddr(String token,Integer assetId,Integer pageSize,Integer pageIndex,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		if(pageSize==null)
			pageSize=100;
		if(pageIndex==null)
			pageIndex=0;
		
		ModelDAO dao=new ModelDAO();
		
//		String sql="select t.txId,t.time,";
//        sql+=" IF(t.from_rootAddress='"+luser.getWalletAddress()+"',u2.realName,u1.realName) as userName,";
//        sql+=" IF(t.from_rootAddress='"+luser.getWalletAddress()+"',t.rootAddress,t.from_rootAddress) as rootAddress,";
//        sql+=" IF(t.from_rootAddress='"+luser.getWalletAddress()+"',-1,1) as way,";
//        sql+=" t.assetId,";
//        sql+=" IF(t.from_rootAddress='"+luser.getWalletAddress()+"',-1*t.amount,t.amount) as qty";
//        sql+=" from t_tx_detail t";
//        sql+=" left join t_user u1 on t.from_rootAddress=u1.walletAddress";
//        sql+=" left join t_user u2 on t.rootAddress=u2.walletAddress";
//        sql+=" where t.way=1 and t.assetId=" + assetId;
//        sql+=" 	     and t.from_rootAddress!=t.rootAddress";
//        sql+="       and (t.from_rootAddress='"+luser.getWalletAddress()+"' or t.rootAddress='"+luser.getWalletAddress()+"')";
//        sql+=" order by time desc";  
        
        String sql="select w.txId,w.time,u.realName as userName,w.rootAddress,w.way,w.assetId,w.way*w.amount as qty";
        sql+=" from(";
        sql+="   select t.txId,t.time,";
        sql+=" 	 IF(t.from_rootAddress='"+luser.getWalletAddress()+"',t.rootAddress,t.from_rootAddress) as rootAddress,";
        sql+=" 	 IF(t.from_rootAddress='"+luser.getWalletAddress()+"',-1,1) as way,";
        sql+=" 	 t.assetId,";
        sql+=" 	 t.amount";
        sql+=" 	 from t_tx_detail t";
        sql+=" 	 where t.way=1 and t.assetId="+assetId;
        sql+=" 				 and t.from_rootAddress!=t.rootAddress";
        sql+=" 				 and (t.from_rootAddress='"+luser.getWalletAddress()+"' or t.rootAddress='"+luser.getWalletAddress()+"')";         
        sql+=" )w";
        sql+=" left join t_user u on w.rootAddress=u.walletAddress";
        sql+=" order by time desc";
        
		SqlList list=dao.queryByPaging(sql, pageIndex, pageSize);
				
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("data", list);
		return result;
	}
	
	//获取我的钱包锁定状态 1-锁定 0-未锁定
	@ResponseBody
	@RequestMapping(value="/getwalletlock",method=RequestMethod.POST)
	public JSONObject getWalletLock(String token,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		SqlMap sm=dao.M("t_user").where("id="+luser.getUserid()).selectOne("id,walletLock");
		int lock=0;
		if(sm!=null)
			lock=sm.getInt("walletLock");
		
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("walletLock", lock);
		return result;
	}
	
	//锁定用户钱包,lock=0 不锁定  lock=1锁定
	@ResponseBody
	@RequestMapping(value="/setwalletlock",method=RequestMethod.POST)
	public JSONObject setWalletLock(String token,int lock,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		int res=dao.M("t_user").where("id="+luser.getUserid()).update("walletLock="+lock);
		if(res<=0){
			result.put("success", false);
			result.put("code", 2);
			result.put("message", "写入数据库失败！");
			return result;
		}
		
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");
		return result;
	}
	
	//绑定钱包地址，但不执行授权
//	@ResponseBody
//	@RequestMapping(value="/boundrootaddress",method=RequestMethod.POST)
//	public JSONObject boundRootAddress(String token,String address,
//			HttpServletRequest request,HttpServletResponse response){
//		JSONObject result=new JSONObject();
//		
//		if(address==null||address.isEmpty()){
//			result.put("success", false);
//			result.put("code", 2);
//			result.put("message", "地址为空无效");
//			return result;
//		}	
//		
//		LoginUser luser=ApiLoginContext.getUser(token);
//		if(luser==null){
//			result.put("success", false);
//			result.put("code", 101);
//			result.put("message", "无效token");
//			return result;
//		}	
//		
//		luser.setWalletAddress(address);
//		
//		//2、绑定根地址
//		ModelDAO dao=new ModelDAO();
//		try{
//			dao.exec("update t_user set walletAddress='"+address+"' where id="+luser.getUserid());
//		}catch(Exception e){
//			e.printStackTrace();
//			WwLog.getLogger(this).error("保存钱包地址到用户表异常："+e.getMessage());
//		}
//		
//		result.put("success", true);	
//		result.put("code", 0);
//		result.put("message", "成功");
//		return result;
//	}
	
	//授权并绑定根地址
	@ResponseBody
	@RequestMapping(value="/authrootaddress",method=RequestMethod.POST)
	public JSONObject authRootAddress(String token,String address,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();	
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}	
		
		if(address==null||address.isEmpty()){
			result.put("success", false);
			result.put("code", 2);
			result.put("message", "地址为空无效");
			return result;
		}	
		
		//1、这里调用钱包node接口，认证根地址 
		WwBitcoinClient client=WwBitcoinClient.instMainNode();
		
		//经验地址是否已认证证
		String address2=client.getPrimeAddress(address);
		if(address2!=null&&!address2.isEmpty()&&address2.equals(address)){
			//已经认证授权
		}else{		
			//认证授权地址		
			String signprogram="/home/yyw/projects/scripts/opsigner.py";
			String res=client.authPrimeAddr(signprogram, address);
			if(res==null){
				result.put("success", false);
				result.put("code", 3);
				result.put("message", "调用授权接口失败null");
				WwLog.getLogger(this).error("调用授权接口失败null");
				return result;
			}			
			JSONObject resJson=JSONObject.parseObject(res);
			if(!resJson.getBoolean("success")){
				result.put("success", false);
				result.put("code", 2);
				result.put("message", "调用授权接口失败："+resJson.getString("msg"));
				WwLog.getLogger(this).error("调用授权接口失败："+resJson.getString("msg"));
				return result;
			}
		}
		
		luser.setWalletAddress(address);
		
		//2、绑定根地址
		ModelDAO dao=new ModelDAO();
		try{
			dao.exec("update t_user set walletAddress='"+address+"' where id="+luser.getUserid());
		}catch(Exception e){
			e.printStackTrace();
			WwLog.getLogger(this).error("保存钱包地址到用户表异常："+e.getMessage());
		}
		
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");
		return result;
	}
	
	//认证钱包地址但不绑定
//	@ResponseBody
//	@RequestMapping(value="/authrootaddressonly",method=RequestMethod.POST)
//	public JSONObject authRootAddressOnly(String token,String address,
//			HttpServletRequest request,HttpServletResponse response){
//		JSONObject result=new JSONObject();
//		
//		if(address==null||address.isEmpty()){
//			result.put("success", false);
//			result.put("code", 2);
//			result.put("message", "地址为空无效");
//			return result;
//		}	
//		
//		LoginUser luser=ApiLoginContext.getUser(token);
//		if(luser==null){
//			result.put("success", false);
//			result.put("code", 101);
//			result.put("message", "无效token");
//			return result;
//		}		
//		
//		//1、这里调用钱包node接口，认证根地址 
//		WwBitcoinClient client=WwBitcoinClient.instMainNode();
//		
//		//经验地址是否已验证
//		String address2=client.getPrimeAddress(address);
//		if(address2!=null&&!address2.isEmpty()&&address2.equals(address)){
//			//已经认证授权
//			result.put("message", "次地址已经认证授权过，无需在授权");
//		}else{		
//			//认证授权地址		
//			String signprogram="/home/yyw/scripts/opsigner.py";
//			String res=client.authPrimeAddr(signprogram, address);
//			if(res==null){
//				result.put("success", false);
//				result.put("code", 3);
//				result.put("message", "调用授权接口失败null");
//				WwLog.getLogger(this).error("调用授权接口失败null");
//				return result;
//			}			
//			JSONObject resJson=JSONObject.parseObject(res);
//			if(!resJson.getBoolean("success")){
//				result.put("success", false);
//				result.put("code", 4);
//				result.put("message", "调用授权接口失败："+resJson.getString("msg"));
//				WwLog.getLogger(this).error("调用授权接口失败："+resJson.getString("msg"));
//				return result;
//			}
//			result.put("message", "认证授权成功");
//		}
//		
//		result.put("success", true);	
//		result.put("code", 0);
//		return result;
//	}
	
	//保存交易记录
	@ResponseBody
	@RequestMapping(value="/savetxlog",method=RequestMethod.POST)
	public JSONObject savetxlog(String token,Integer assetId,String assetName,
			Long prevBalance,Long qty,String toAddress,String toName,
			Integer isSuccess,String error,String txId,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		SqlMap sm=new SqlMap();
		sm.put("userId", luser.getUserid());
		sm.put("time", WwSystem.now());
		sm.put("assetId", assetId);
		sm.put("assetName", assetName);
		sm.put("prevBalance", prevBalance);
		sm.put("qty", qty);
		sm.put("toAddress", toAddress);
		sm.put("toName", toName);
		sm.put("isSuccess", isSuccess);
		
		if(error==null){
			error="";
		}else{
			error=error.replaceAll("'", "‘");
			error=error.replaceAll("\\n", "");
			error=error.replaceAll("\\r", "");
		}		
		sm.put("error", error);
		
		sm.put("txId", txId);
		
		long res=dao.M("t_tx_log").insert(sm);
		if(res<=0){
			result.put("success", false);
			result.put("code", 2);
			result.put("message", "写入数据库失败！");
			return result;
		}
		
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/getblockcount",method=RequestMethod.POST)
	public JSONObject getBlockCount(String token,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		Config cfg=Config.getInst();
		WwBitcoinClient client=new WwBitcoinClient(cfg.nodeIP,cfg.rpcUser,cfg.rpcPassword,cfg.nodePort);
		
		int blockCount=client.getBlockCount();
				
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("blockCount", blockCount);
		
		
		return result;
	}
	
	//从数据库获取未花销的资产
	@ResponseBody
	@RequestMapping(value="/listunspent",method=RequestMethod.POST)
	public JSONObject listUnspent(String token,Long userId,Integer assetId,Integer pageIndex,Integer pageSize,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		
		if(pageIndex==null)
			pageIndex=0;
		if(pageSize==null)
			pageSize=0;
		
		int from=pageIndex*pageSize;
		int count=pageSize;
		
		SqlList list=dao.query("call p_listunspent("+userId+","+assetId+","+from+","+count+")");
				
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("data", list);
		
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/listunspent2",method=RequestMethod.POST)
	public JSONObject listUnspent2(String token,String rootAddress,Integer assetId,Integer pageIndex,Integer pageSize,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		
		if(pageIndex==null)
			pageIndex=0;
		if(pageSize==null)
			pageSize=20;
		
		int from=pageIndex*pageSize;
		int count=pageSize;
		
		SqlList list=dao.query("call p_listunspent2('"+rootAddress+"',"+assetId+","+from+","+count+")");
				
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("data", list);
		
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/sendrawtransaction",method=RequestMethod.POST)
	public JSONObject sendRawTransaction(String token,String hexTransaction,String payPsw,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		if(hexTransaction==null||hexTransaction.isEmpty()){
			result.put("success", false);
			result.put("code", 2);
			result.put("message", "交易hex字符串为空无效");
			return result;
		}	
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		
		//验证转账密码
		if(payPsw != null){ //条件是为了兼容老钱包
			//获取数据库中的密码
			SqlMap usm=dao.M("t_user").where("id="+luser.getUserid()).selectOne("transPassword");
			if(usm.getString("transPassword").isEmpty()){
				result.put("success", false);
				result.put("code", 108);
				result.put("message", "没有设置过转账密码，请进入安全中心设置转账密码，然后在交易。");
				return result;
			}
			String en_payPsw=Md5Encrypt.md5(payPsw);
			if(!en_payPsw.equals(usm.getString("transPassword"))){
				result.put("success", false);
				result.put("code", 7);
				result.put("message", "输入的转账密码不正确.");
				return result;
			}
		}
		
		//1、这里调用钱包node接口，认证根地址 
		WwBitcoinClient client=WwBitcoinClient.inst();
		
		//发送交易
		String resJson=client.sendrawtransaction(hexTransaction);
		if(resJson==null){
			if(client.error!=null
					&&!client.error.isEmpty()
					&&client.error.toLowerCase().indexOf("dest-id-auth-error")>=0){
				result.put("success", false);
				result.put("code", 3);
				result.put("message", "您的钱包地址没有认证或已挂失,不能交易！");
				WwLog.getLogger(this).error("您的钱包地址没有认证或已挂失："+client.error);
				return result;
			}else{
				result.put("success", false);
				result.put("code", 3);
				result.put("message", "交易失败，确认转账地址是瑞资链正式钱包已认证地址和无误后请重试！");
				WwLog.getLogger(this).error("调用sendrawtransaction接口异常："+client.error);
				return result;
			}
		}else{		
			try{
				JSONObject json=JSONObject.parseObject(resJson);
				if(json==null){
					if(resJson!=null
							&&!resJson.isEmpty()
							&&resJson.toLowerCase().indexOf("dest-id-auth-error")>=0){
						result.put("success", false);
						result.put("code", 4);
						result.put("message", "您的钱包地址没有认证或已挂失,不能交易！");
						WwLog.getLogger(this).error("您的钱包地址没有认证或已挂失："+resJson);
						return result;
					}else{
						result.put("success", false);
						result.put("code", 4);
						//result.put("message", "调用sendrawtransaction失败："+resJson);
						result.put("message", "交易失败，确认转账地址是瑞资链正式钱包已认证地址和无误后请重试！");
						WwLog.getLogger(this).error("调用sendrawtransaction接口失败:"+resJson);
						return result;
					}
				}
				
				if(json.getBoolean("success")){
					result.put("success", true);	
					result.put("code", 0);
					result.put("message", "成功");
					result.put("txid", json.getString("txid"));
					return result;
				}else{
					result.put("success", false);	
					result.put("code", 5);
					//result.put("message", resJson);
					result.put("message", "交易失败，确认转账地址是瑞资链正式钱包已认证地址和无误后请重试！");
					WwLog.getLogger(this).error("调用sendrawtransaction接口失败:"+resJson);
					return result;
				}
				
			}catch(Exception e){
				if(resJson!=null
						&&!resJson.isEmpty()
						&&resJson.toLowerCase().indexOf("dest-id-auth-error")>=0){
					result.put("success", false);
					result.put("code", 6);
					result.put("message", "您的钱包地址没有认证或已挂失,不能交易！");
					WwLog.getLogger(this).error("您的钱包地址没有认证或已挂失："+resJson);
					return result;
				}else{
					result.put("success", false);
					result.put("code", 6);
					//result.put("message", "调用sendrawtransaction失败："+resJson);
					result.put("message", "交易失败，确认转账地址是瑞资链正式钱包已认证地址和无误后请重试！");
					WwLog.getLogger(this).error("调用sendrawtransaction接口失败"+resJson);
					return result;
				}
			}
		}		
		
	}
	
	@ResponseBody
	@RequestMapping(value="/decoderawtransaction",method=RequestMethod.POST)
	public JSONObject decodeRawTransaction(String token,String hexTransaction,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		if(hexTransaction==null||hexTransaction.isEmpty()){
			result.put("success", false);
			result.put("code", 2);
			result.put("message", "交易hex字符串为空无效");
			return result;
		}	
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}		
		
		//1、这里调用钱包node接口，认证根地址 
		WwBitcoinClient client=WwBitcoinClient.inst();
		
		//解码
		String resJson=client.decoderawtransaction(hexTransaction);
		if(resJson==null){
			result.put("success", false);
			result.put("code", 3);
			result.put("message", "调用decoderawtransaction异常："+client.error);
			WwLog.getLogger(this).error("调用decoderawtransaction接口异常："+client.error);
			return result;
		}else{		
			result.put("success", true);
			result.put("code", 3);
			result.put("message", "");
			result.put("json", resJson);
			return result;
		}		
		
	}
	
	@ResponseBody
	@RequestMapping(value="/getbalance",method=RequestMethod.POST)
	public JSONObject getBalance(String token,long userId,Integer assetId,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		
		SqlList list=dao.query("call p_getbalance("+userId+","+assetId+")");
		long balance=0;
		if(list.size()>0){
			balance=list.get(0).getLong("amount");
		}
				
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("balance", balance);
		
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/getbalancelist",method=RequestMethod.POST)
	public JSONObject getBalanceList(String token,long userId,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		
		SqlList list=dao.query("call p_getbalance2('"+userId+"')");
				
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("data", list);
		
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/getbalancebyaddr",method=RequestMethod.POST)
	public JSONObject getBalanceByAddr(String token,String rootAddress,Integer assetId,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		
		SqlList list=dao.query("call p_getbalancebyaddr('"+rootAddress+"',"+assetId+")");
		long balance=0;
		if(list.size()>0){
			balance=list.get(0).getLong("amount");
		}
				
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("balance", balance);
		
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/getbalancelistbyaddr",method=RequestMethod.POST)
	public JSONObject getBalanceListByAddr(String token,String rootAddress,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		
		SqlList list=dao.query("call p_getbalancebyaddr2('"+rootAddress+"')");
				
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("data", list);
		
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/searchuser",method=RequestMethod.POST)
	public JSONObject searchUser(String token,String key,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		
		SqlList list=dao.query("select id,phoneNum,realName,walletAddress from t_user where realName like '%"+key+"%' or phoneNum like '%"+key+"%' or walletAddress like '"+key+"%'");
				
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("data", list);
		
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/clearuseraddress",method=RequestMethod.POST)
	public JSONObject clearUserAddress(String token,Long userId,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		if(!luser.getUsername().equals("18787008828")){
			result.put("success", false);
			result.put("code", 1);
			result.put("message", "你没有此接口的权限");
			return result;			
		}
		
		ModelDAO dao=new ModelDAO();
		
		dao.exec("update t_user set walletAddress='' where id="+userId);
			
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");		
		
		return result;
	}
	
	//获取交易详情
	@ResponseBody
	@RequestMapping(value="/gettxdetail")
	public JSONObject getTxDetail(String token,String txId,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		ModelDAO dao=new ModelDAO();
		
		String txSql="select txid,blockhash,time,confirmations from t_tx where txid='"+txId+"'";
		SqlMap tx=dao.queryToFirst(txSql);
		
		String vinSql="select txid,userid,u.realname,address,amount,way from t_tx_detail t";
		vinSql+=" left join t_user u on t.userid=u.id";
		vinSql+=" where way=-1 and  txid='"+txId+"'";
		SqlList vinList=dao.query(vinSql);
		
		String voutSql="select txid,userid,u.realname,address,amount,way from t_tx_detail t";
		voutSql+=" left join t_user u on t.userid=u.id";
		voutSql+=" where way=1 and  txid='"+txId+"'";
		SqlList voutList=dao.query(voutSql);
		
		
		result.put("baseInfo", tx);		
		result.put("vinList", vinList);	
		result.put("voutList", voutList);	
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");		
		
		return result;
	}
	
	//不在需要此接口
    //指派wizardid到账本,每次挂失找回资产后执行
//	@ResponseBody
//	@RequestMapping(value="/assignwizardid",method=RequestMethod.POST)
//	public JSONObject assignWizardid(String token,
//			HttpServletRequest request,HttpServletResponse response){
//		JSONObject result=new JSONObject();
//		
//		LoginUser luser=ApiLoginContext.getUser(token);
//		if(luser==null){
//			result.put("success", false);
//			result.put("code", 101);
//			result.put("message", "无效token");
//			return result;
//		}
//		
//		//1、这里调用钱包node接口，认证根地址
//		WwBitcoinClient client=WwBitcoinClient.instMainNode();
//		
//		String signprogram="/home/yyw/projects/scripts/opsigner.py";
//		String wizardprogram="/home/yyw/projects/scripts/wizard.py";
//		String jsonstr=client.getWizardidFromRemote();		
//		
//		JSONObject resJson=JSONObject.parseObject(jsonstr);
//		if(!resJson.getBoolean("success")){
//			result.put("success", false);
//			result.put("code", 2);
//			result.put("message", "调用wizard.py获取wizardid失败："+resJson.getString("msg"));
//			WwLog.getLogger(this).error("调用wizard.py获取wizardid失败："+resJson.getString("msg"));
//			return result;
//		}
//		
//		String wizardid=resJson.getString("wizardid");		
//		
//		String jsonstr2=client.assignWizardid(signprogram, wizardid);
//		JSONObject resJson2=JSONObject.parseObject(jsonstr2);
//		if(!resJson2.getBoolean("success")){
//			result.put("success", false);
//			result.put("code", 3);
//			result.put("wizardid", wizardid);
//			result.put("message", "调用operatoraction失败："+resJson2.getString("msg"));
//			WwLog.getLogger(this).error("调用operatoraction失败："+resJson2.getString("msg"));
//			return result;
//		}
//		
//		result.put("message", "指派wizardid到账本成功");		
//		result.put("success", true);	
//		result.put("code", 0);
//		result.put("wizardid", wizardid);
//		result.put("txid", resJson2.getString("txid"));
//		return result;
//	}
	
	//取消老地址验证(挂失第一步)
	@ResponseBody
	@RequestMapping(value="/revokeaddress",method=RequestMethod.POST)
	public JSONObject revokeAddress(String token,String oldAddress,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		//String oldAddress=luser.getWalletAddress();
		if(oldAddress==null || oldAddress.isEmpty()){
			result.put("success", false);
			result.put("code", 3);
			result.put("message", "用户注册的地址不能为空");
			return result;
		}	
		
		WwBitcoinClient client=WwBitcoinClient.instMainNode();		
		String signprogram="/home/yyw/projects/scripts/opsigner.py";
		//String wizardprogram="/home/yyw/projects/scripts/wizard.py";
		
		//老地址取消认证		
		String jsonstr=client.revokePrimeAddr(signprogram, oldAddress);
		JSONObject resJson=JSONObject.parseObject(jsonstr);
		if(!resJson.getBoolean("success")){
			result.put("success", false);
			result.put("code", 4);
			result.put("message", "老地址取消认证失败："+resJson.getString("msg"));
			WwLog.getLogger(this).error("老地址取消认证失败："+resJson.getString("msg"));
			return result;
		}
		String revokeTxId=resJson.getString("txid");
		
		result.put("message", "找回资产成功");		
		result.put("success", true);	
		result.put("code", 0);
		result.put("txid", revokeTxId);
		return result;
	}
	
	//申请挂失：保存挂失记录->取消老地址认证->新地址认证->保存新地址到用户.
	@ResponseBody
	@RequestMapping(value="/reportloss",method=RequestMethod.POST)
	public JSONObject reportLoss(String token,String newAddress,String phoneNum,String phoneCode,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}
		
		if(newAddress==null||newAddress.isEmpty()){
			result.put("success", false);
			result.put("code", 2);
			result.put("message", "新地址不能为空");
			return result;
		}	
		
		String oldAddress=luser.getWalletAddress();
		if(oldAddress==null || oldAddress.isEmpty()){
			result.put("success", false);
			result.put("code", 3);
			result.put("message", "老地址不能为空");
			return result;
		}
		
		//验证用户表里的钱包地址是否已经挂失未处理
		boolean bb=_isReportLossing(luser.getUserid(),oldAddress);	
		if(bb){
			result.put("success", false);
			result.put("code", 5);
			result.put("message", "上一次的挂失还在处理中，请等代处理！");
			return result;
		}
		
		//验证短信验证码
		PhoneValidCodeInfo pvc=ApiLoginContext.getPhoneValidCode(phoneNum);		
		if(pvc==null||phoneCode==null){
			result.put("success", false);
			result.put("code", 3);
			result.put("message", "没有验证码");
			
			return result;
		}		
		if(!pvc.ValidCode.equals(phoneCode)){
			result.put("success", false);
			result.put("code", 2);
			result.put("message", "验证码错误");
			
			return result;
		}		
		ApiLoginContext.removePhoneValidCode(phoneNum);
		
		//保存挂失记录
		ModelDAO dao=new ModelDAO();
		try{
			SqlMap sm=new SqlMap();
			sm.put("userId", luser.getUserid());
			sm.put("walletAddressOld", oldAddress);
			sm.put("walletAddressNew", newAddress);
			sm.put("reportTime", new Date());
			sm.put("status", 0);
			long res=dao.M("t_reportloss").insert(sm);
			if(res<=0){
				result.put("success", false);
				result.put("code", 8);
				result.put("message", "保存挂失申请(user="+luser.getUserid()+"|"+luser.getRealName()+")失败："+dao.Message);
				WwLog.getLogger(this).error("保存挂失申请(user="+luser.getUserid()+"|"+luser.getRealName()+")失败："+dao.Message);
				return result;
			}
		}catch(Exception e){
			result.put("success", false);
			result.put("code", 8);
			result.put("message", "保存挂失申请(user="+luser.getUserid()+"|"+luser.getRealName()+")失败："+e.getMessage());
			WwLog.getLogger(this).error("保存挂失申请(user="+luser.getUserid()+"|"+luser.getRealName()+")失败："+e.getMessage());
			return result;
		}
		
		WwBitcoinClient client=WwBitcoinClient.instMainNode();		
		String signprogram="/home/yyw/projects/scripts/opsigner.py";
		String wizardprogram="/home/yyw/projects/scripts/wizard.py";		
		
		//老地址取消认证
		String revokeTxId="";
		String jsonstr=client.revokePrimeAddr(signprogram, oldAddress);
		JSONObject resJson=JSONObject.parseObject(jsonstr);
		if(!resJson.getBoolean("success")){
//			result.put("success", false);
//			result.put("code", 4);
//			result.put("message", "老地址取消认证失败："+resJson.getString("msg"));
//			WwLog.getLogger(this).error("老地址取消认证失败："+resJson.getString("msg"));
//			return result;
			
			//没成功表示此地址之前已经没有认证，为了安全得由老朱出具检查未认证的方法，getprimeaddr只能验证新地址，无法验证取消的老地址。
		}else{
			revokeTxId=resJson.getString("txid");
		}		
		
		//新地址认证
		String address3=client.getPrimeAddress(newAddress);
		if(address3!=null&&!address3.isEmpty()&&!address3.equals("null")){
			//已经认证授权,无需再认证
		}else{
			//未认证，执行认证
			String jsonstr2=client.authPrimeAddr(signprogram, newAddress);
			JSONObject resJson2=JSONObject.parseObject(jsonstr2);
			if(!resJson2.getBoolean("success")){
				result.put("success", false);
				result.put("code", 5);
				result.put("message", "新地址("+newAddress+")认证失败："+resJson2.getString("msg"));
				WwLog.getLogger(this).error("新地址("+newAddress+")取证失败："+resJson2.getString("msg"));
				return result;
			}
		}		
		
		//保存新地址到用户
		try{
			dao.exec("update t_user set walletAddress='"+newAddress+"' where id="+luser.getUserid());
		}catch(Exception e){
			result.put("success", false);
			result.put("code", 8);
			result.put("message", "保存新地址("+newAddress+")到用户异常："+e.getMessage());
			WwLog.getLogger(this).error("保存新地址("+newAddress+")到用户异常："+e.getMessage());
			return result;
		}
		
		result.put("message", "挂失申请已成功提交，请等待处理。");		
		result.put("success", true);	
		result.put("code", 0);
		result.put("revokeTxId", revokeTxId); //老地址注销txid
		return result;
	}
	
	//是否已申请挂失
	@ResponseBody
	@RequestMapping(value="/isreportlossing",method=RequestMethod.POST)
	public JSONObject isReportLossing(String token,String walletAddress,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		LoginUser luser=ApiLoginContext.getUser(token);
		if(luser==null){
			result.put("success", false);
			result.put("code", 101);
			result.put("message", "无效token");
			return result;
		}

		boolean bb=_isReportLossing(luser.getUserid(),walletAddress);		
		
		result.put("message", "成功");
		result.put("success", true);
		result.put("code", 0);
		result.put("isReportLossing", bb);
		return result;
	}
	
	/*******非接口*********/
	//是否正在挂失处理中
	private boolean _isReportLossing(long userId,String walletAddress){
		ModelDAO dao=new ModelDAO();
		String sql="select id from t_reportloss where (status!=2 and status!=-2) and userId=" + userId + " and (walletAddressOld='"+walletAddress+"' or walletAddressNew='"+walletAddress+"')";
		SqlList list=dao.query(sql);
		boolean bb=list.size()>0;
		
		return bb;
	}
	

}
