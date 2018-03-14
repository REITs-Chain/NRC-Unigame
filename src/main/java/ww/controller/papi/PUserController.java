package ww.controller.papi;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BankcardVerif;
import model.Bonusaddress;
import model.Loginlog;
import model.Rawasset;
import model.User;
import model.UserRisk;
import model.common.CerStatusEnum;
import model.rongCloud.TokenResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.support.odps.udf.JSONContains;
import com.taobao.api.internal.parser.json.JsonConverter;

import ww.authen.ApiLoginContext;
import ww.authen.ApiLoginContext.ImageValidCodeInfo;
import ww.authen.ApiLoginContext.PhoneValidCodeInfo;
import ww.authen.LoginUser;
import ww.authen.LoginUserUtil;
import ww.common.DbModel;
import ww.common.FileUploadUtil;
import ww.common.Md5Encrypt;
import ww.common.ModelDAO;
import ww.common.SmsUtil_heng;
import ww.common.SmsUtil_hk;
import ww.common.SqlList;
import ww.common.SqlMap;
import ww.common.ValidateCode;
import ww.common.WwSystem;
import ww.controller.BaseController;
import ww.dao.BankcardVerifMapper;
import ww.dao.UserRiskMapper;
import ww.idcard.IDCard;
import ww.rong.RongCloudUtil;
import ww.service.admin.BankcardVerifService;
import ww.service.admin.BonusaddressService;
import ww.service.admin.LoginlogService;
import ww.service.admin.RawassetService;
import ww.service.admin.UserRiskService;
import ww.service.admin.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)  //跨域post请求
@Controller
@RequestMapping(value="/papi/puser")
public class PUserController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	//获取图片验证码
	@ResponseBody
	@RequestMapping("/getvalidcode")
	public JSONObject getValidcode(HttpServletRequest request,HttpServletResponse response) throws Exception{  
		ValidateCode vCode = new ValidateCode(100,46,4,10); 
		String validCode=vCode.createNewCode();
		String imageToken=WwSystem.UUID();
		ApiLoginContext.setImageValidCode(imageToken, validCode);
		
		//大于5分钟后清除缓存
		//ApiLoginContext.clearImageValidCode();
		
		JSONObject result=new JSONObject();
		result.put("success", true);
		result.put("code", 0);
		result.put("message", "成功");
		result.put("token", imageToken);
		result.put("imageurl", "api/login/validcodeimage?img="+imageToken);
		
		return result;
	}
	
	//访问验证图片URL
	@RequestMapping("/validcodeimage")
	public void ValidcodeImage(String img,HttpServletRequest request,HttpServletResponse response) throws Exception{  
		String imageToken=img;
		response.setContentType("image/jpeg");  
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);  
		ValidateCode vCode = new ValidateCode(100,46,4,50);  
		
	    ImageValidCodeInfo vcinfo=ApiLoginContext.getImageValidCode(imageToken);
	    if(vcinfo==null){
	    	throw new Exception("无效的token");
	    }
		
		vCode.createImage(vcinfo.ValidCode);
		
		//System.out.print(token+":"+vCode.getCode()); 
		
		vCode.write(response.getOutputStream()); 
	      
	} 
	
	//登录
	//@CrossOrigin(origins = "*", maxAge = 3600)  //跨域post请求
	@ResponseBody	
	@RequestMapping(value={"/dologin"},method=RequestMethod.POST)
	public JSONObject DoLogin(String imageToken, //注意这个token不是登录的token，而是获取图片验证码的token
			HttpServletRequest request,
			HttpServletResponse response){
		
		JSONObject result=new JSONObject();		
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		int loginType=ww.common.WwSystem.ToInt(request.getParameter("loginType")); //登录者： 1-钱包登录 0-其他其他程序(网站) 
		String validateCode=request.getParameter("validateCode");
		String ip=request.getParameter("ip");
		String location=request.getParameter("location");
		String version=request.getParameter("version");
		
		String sysVersion=WwSystem.getSystemVar("walletVersion");
		
		if(version==null||version.isEmpty()){
			result.put("success", false);
			result.put("code", 201);
			result.put("newVersion", sysVersion);
			result.put("message", "版本太低，请下载新钱包");
			return result;
		}
		
		if(version.trim().compareTo("2.0.2")<0 || version.trim().compareTo(sysVersion)<0){
			result.put("success", false);
			result.put("code", 201);
			result.put("newVersion", sysVersion);
			result.put("message", "版本太低，请下载新钱包");
			return result;
		}
		
		LoginUser user= LoginUserUtil.getByUsername(username);
		if(user==null){
			result.put("success", false);
			result.put("code", 1);
			result.put("message", "用户名不存在");
			
			return result;
		}
		
		if(user.getType()!=0){ //0普通用户
			result.put("success", false);
			result.put("code", 7);
			result.put("message", "非普通用户不能使用本钱包！");
			
			return result;
		}
	
		int times=user.getLoginTimes();
		
		if(loginType!=1){//非钱包登录需要验证码
			times++;
			user.setLoginTimes(times); //更新登录次数
			LoginUserUtil.updateLoginTimes(times, user.getUserid());
			
			//验证码比较
			if(times>=3){
				ImageValidCodeInfo vcinfo=ApiLoginContext.getImageValidCode(imageToken);
			    if(vcinfo==null){
			    	result.put("success", false);
					result.put("code", 2);
					result.put("loginTimes", user.getLoginTimes());
					result.put("message", "无效token，非法登录");
					LoginUserUtil.SaveLoginLog(user.getUserid(), ip, location, "无效token，非法登录");
					return result;
			    }
				if(!vcinfo.ValidCode.equalsIgnoreCase(validateCode)){
					result.put("success", false);
					result.put("code", 3);
					result.put("loginTimes", user.getLoginTimes());
					result.put("message", "验证码输入错误");
					LoginUserUtil.SaveLoginLog(user.getUserid(), ip, location, "验证码输入错误");
					return result;
				}
			}
		}
		
		//密码比较
		String en_pw=Md5Encrypt.md5(password);
		if(!user.getPassword().equals(en_pw)){
			result.put("success", false);
			result.put("code", 4);
			result.put("loginTimes", user.getLoginTimes());
			result.put("message", "用户名或密码错误");
			
			LoginUserUtil.SaveLoginLog(user.getUserid(), ip, location, "密码错误");
			
			return result;
		}
						
		result.put("success", true);
		result.put("code", 0);
		result.put("message", "登录成功");
		result.put("loginTimes", user.getLoginTimes());
		//获取融云token
		String userid= String.valueOf(user.getUserid());
		TokenResult rongToken= RongCloudUtil.getToken(userid, user.getNickName(), "http://www.rongcloud.cn/images/logo.png");
		if (rongToken!=null && rongToken.getCode()==200){ 
			 result.put("rongCloudToken", rongToken);
		}else{
			 result.put("success", false);
			 result.put("code", 8);
			 result.put("message", "获取聊天token失败:"+RongCloudUtil.Message);
			 return result;
		}
		String token = rongToken.getToken();
		DbModel dao1=new DbModel();
		dao1.table("t_user")
			.where("id=:id")
			.bind("id", user.getUserid())
			.bind("rctoken",token)
			.update("rongCloudToken=:rctoken");
		
		//make login token
		String loginToken=Md5Encrypt.md5(user.getUsername()+user.getPassword()+(new Date()).getTime());
		ApiLoginContext.setToken(user, loginToken);
		result.put("token", loginToken);
		
		result.put("id", user.getUserid());
		result.put("nickName", user.getNickName());
		result.put("realName", user.getRealName());
		String avatar="";
		if(user.getAvatar()==null||user.getAvatar().isEmpty()||user.getAvatar().equals("")){
			avatar="/public/image_list/userhead/default_user.png";
		}else{
			avatar="/public/image_list/userhead/"+user.getAvatar();
		}
		result.put("avatar", avatar);
		
		result.put("certificationStatus", user.getCertificationStatus());
		result.put("bankCardVerifStatus", user.getBankCardVerifStatus());
		result.put("walletAddress", user.getWalletAddress());
		if(user.getDefaultLanguage()==null||user.getDefaultLanguage().isEmpty()){
			user.setDefaultLanguage("zh");
		}
		result.put("defaultLanguage", user.getDefaultLanguage());		
		
		ModelDAO dao=new ModelDAO();
		if(user.getAesKey()==null||user.getAesKey().isEmpty()){
			String aesKey=WwSystem.UUID()+"_tyu";
			boolean bb=dao.exec("update t_user set aesKey='"+aesKey+"' where id="+user.getUserid());
			if(!bb){
				result.put("success", false);
				result.put("code", 5);
				result.put("message", "保存aesKey到数据库失败:"+dao.Message);
				return result;
			}
			user.setAesKey(aesKey);
		}
		if(user.getAesIv()==null||user.getAesIv().isEmpty()){
			String aesIv=WwSystem.UUID()+"_pfg";
			boolean bb=dao.exec("update t_user set aesIv='"+aesIv+"' where id="+user.getUserid());
			if(!bb){
				result.put("success", false);
				result.put("code", 6);
				result.put("message", "保存aesIv到数据库失败:"+dao.Message);
				return result;
			}
			user.setAesIv(aesIv);
		}
		result.put("aesKey", user.getAesKey());
		result.put("aesIv", user.getAesIv());
		
		
		user.setLoginTimes(0);
		LoginUserUtil.updateLoginTimes(0, user.getUserid());
		LoginUserUtil.SaveLoginLog(user.getUserid(), ip, location, "登录成功");
		
		return result;
	}
	
	//注册会员
	@ResponseBody
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public JSONObject register(String phoneNum,String phoneValidCode, String password,
			String inviteCode,String areaNum,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		if(phoneNum==null||phoneValidCode==null||password==null){
			result.put("success", false);
			result.put("code", 1);
			result.put("message", "提交数据格式异常");
			
			return result;
		}	
		
		List<User> list=userService.getList("where phoneNum='"+phoneNum+"'");
		if(list.size()>0){
			result.put("success", false);
			result.put("code", 6);
			result.put("message", phoneNum+"此手机号码已经被注册，请换一个手机号码！");
			
			return result;
		}
				
		//手机验证码验证
		PhoneValidCodeInfo pvc=ApiLoginContext.getPhoneValidCode(phoneNum);		
		if(pvc==null||phoneValidCode==null){
			result.put("success", false);
			result.put("code", 3);
			result.put("message", "没有验证码");
			
			return result;
		}		
		if(!pvc.ValidCode.equals(phoneValidCode)){
			result.put("success", false);
			result.put("code", 2);
			result.put("message", "验证码错误");
			
			return result;
		}
		
		ApiLoginContext.removePhoneValidCode(phoneNum);
		
		//默认区号为中国   （+86）
		if (areaNum==null||areaNum.isEmpty()) {
			areaNum="+86";
		}
		
		User data=new User();
		data.setAreaNum(areaNum);
		data.setPhoneNum(phoneNum);
		Date dd=new Date();
		data.setNickName("N_"+dd.getTime());
		data.setType(0); //0-普通用户
		data.setRegTime(WwSystem.now());
		data.setStatus(0); //0-未实名认证，1-实名认证中，2-已实名认证
		data.setCertificationStatus(0);//0-未认证 1-认证中 2-已实名认证 -1-认证失败
		data.setBankCardVerifStatus(0);
		if(inviteCode!=null){
			data.setParentInviteCode(inviteCode);
		}
		
		//加密密码
		String en_pw=Md5Encrypt.md5(password);
		data.setPassword(en_pw);
		
		if(!userService.Save(data)){
			result.put("success", false);
			result.put("code", 5);
			result.put("message", "保存注册信息失败");
			
			return result;
		}
		
		result.put("success", true);
		result.put("code", 0);
		result.put("message", "注册成功");
		
		return result;
	}
	
	//忘记密码
	@ResponseBody
	@RequestMapping(value="/forgetpassword",method=RequestMethod.POST)
	public JSONObject ForgetPassword(String phoneNum,
			String password,
			String phoneValidCode, 
			HttpServletRequest request,
			HttpServletResponse response){
		
		JSONObject result=new JSONObject();
		
		ModelDAO dao=new ModelDAO();
		SqlMap user=dao.M("t_user").where("phoneNum='"+phoneNum+"'").selectOne();
		if(user==null){
			result.put("success", false);
			result.put("code", 1);
			result.put("message", "账号不存在");
			
			return result;
		}
		
		//手机验证码验证
		PhoneValidCodeInfo pvc=ApiLoginContext.getPhoneValidCode(phoneNum);		
		if(pvc==null||phoneValidCode==null){
			result.put("success", false);
			result.put("code", 2);
			result.put("message", "没有验证码");
			
			return result;
		}	
		pvc.Times++;
		if(pvc.Times>3){
			result.put("success", false);
			result.put("code", 3);
			result.put("message", "你已超过三次");
			
			return result;
		}
		if(!pvc.ValidCode.equals(phoneValidCode)){
			result.put("success", false);
			result.put("code", 4);
			result.put("message", "验证码错误");
			
			return result;
		}
		
	    ApiLoginContext.removePhoneValidCode(phoneNum);

	    String en_pw=Md5Encrypt.md5(password);
		//修改密码
		dao.M("t_user").where("phoneNum='"+phoneNum+"'").update("password='"+en_pw+"'");
		
		result.put("success", true);
		result.put("code", 0);
		result.put("message", "修改密码成功");
		
		return result;
	}
	
	//获取短信验证码
	@ResponseBody
	@RequestMapping(value="/getregvalidcode")
	public JSONObject getRegValidCode(String phoneNum,String areaNum,
			HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		PhoneValidCodeInfo pvc= ApiLoginContext.getPhoneValidCode(phoneNum);
		int times=0;
		if(pvc!=null){
			times=pvc.Times+1;
			if(times>10){
				result.put("success", false);	
				result.put("code", 2);
				result.put("message", "此号码已达到最大次数");
				
				return result;
			}
		}
		
		//默认区号为中国   （+86）
		if (areaNum==null||areaNum.isEmpty()) {
			areaNum="+86";
		}
		
		String phoneValidCode="";
		if (areaNum.equals("+86")) {
			phoneValidCode=SmsUtil_heng.getNewVerifyNum();
		}else{
			phoneValidCode= SmsUtil_hk.getNewVerifyNum();
		}
		boolean b=SmsUtil_heng.sendVerifyNum_Reg(phoneNum, phoneValidCode);
		if(!b){
			result.put("success", false);	
			result.put("code", 1);
			result.put("message", "发送短信失败:"+SmsUtil_heng.message);
			//result.put("phoneValidCode", phoneValidCode);
			
			return result;
		}
		ApiLoginContext.setPhoneValidCode(phoneNum, phoneValidCode,times);

		
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "短信已发送");
		//result.put("phoneValidCode", phoneValidCode);		
		
		return result;
	}
	
	//上传图片
	@ResponseBody
	@RequestMapping(value="/uploadimage")
	public JSONObject uploadImage(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		//保存图片1
		MultipartFile mFile1 = multipartRequest.getFile("idPhoto1");
		if(mFile1.getSize()>1024*1024*10){
			result.put("success", false);
			result.put("code", 1);
			result.put("message", "省份证件1图片大小不能超过4M");
			
			return result;
		}
		MultipartFile mFile2 = multipartRequest.getFile("idPhoto2");
		if(mFile2.getSize()>1024*1024*10){
			result.put("success", false);
			result.put("code", 2);
			result.put("message", "省份证件2图片大小不能超过4M");
			
			return result;
		}
		MultipartFile mFile = multipartRequest.getFile("photo");
		if(mFile.getSize()>1024*1024*10){
			result.put("success", false);
			result.put("code", 3);
			result.put("message", "手持证件图片大小不能超过4M");
			
			return result;
		}
		
		FileUploadUtil.UploadResult res1=FileUploadUtil.updateOneImage(request, "/private/images/", "idPhoto1",0); 
		if(!res1.success){
			result.put("success", false);
			result.put("code", 4);
			result.put("message", res1.message);
			return result;
		}
		String filename1=res1.newFileName;
		
		//保存图片2
		FileUploadUtil.UploadResult res2=FileUploadUtil.updateOneImage(request, "/private/images/", "idPhoto2",0);
		if(!res2.success){
			result.put("success", false);
			result.put("code", 4);
			result.put("message", res2.message);
			return result;
		}
		String filename2=res2.newFileName;
		
		//保存图片3
		FileUploadUtil.UploadResult res=FileUploadUtil.updateOneImage(request, "/private/images/", "photo",0);
		if(!res.success){
			result.put("success", false);
			result.put("code", 4);
			result.put("message", res.message);
			return result;
		}
		String filename=res.newFileName;
		
		
		result.put("success", true);
		result.put("code", 0);
		result.put("message", "成功");
		result.put("idPhoto1", filename1);
		result.put("idPhoto2", filename2);
		result.put("photo", filename);
		
		
		return result;
	}
	
	
	
	
	
	
}
