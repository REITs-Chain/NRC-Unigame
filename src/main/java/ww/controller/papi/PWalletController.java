package ww.controller.papi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import ww.authen.ApiLoginContext;
import ww.authen.ApiLoginContext.PhoneValidCodeInfo;
import ww.bitcoin.WwBitcoinClient;
import ww.common.ModelDAO;
import ww.common.SmsUtil_heng;
import ww.common.SqlList;
import ww.common.SqlMap;

/**
 * 钱包专用接口
 * @author ww
 *
 */

@CrossOrigin(origins = "*", maxAge = 3600)  //跨域post请求
@Controller
@RequestMapping(value="/papi/pwallet")
public class PWalletController {

	public PWalletController() {
		// TODO Auto-generated constructor stub
	}
	
	@ResponseBody
	@RequestMapping(value="/getappdownurl")
	public JSONObject getAppDownUrl(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		JSONObject data=new JSONObject();
		
		ModelDAO dao=new ModelDAO();
		SqlList list=dao.M("t_appInfo").select();
		for(int i=0;i<list.size();i++){
			SqlMap sm=list.get(i);
			if(sm.getInt("type")==1){
				data.put("ios", sm);
			}else if(sm.getInt("type")==0){
				data.put("android", sm);
			}
		}
		
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");
		result.put("data", data);
		return result;
	}
	
	//获取版本
	@ResponseBody
	@RequestMapping(value="/getversion")
	public JSONObject getVersion(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		ModelDAO dao=new ModelDAO();
		SqlMap sm=dao.M("w_system").where("name='walletVersion'").selectOne("value");
		String version="0.0.1";
		if(sm!=null)
			version=sm.getString("value");
		
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("version", version);
		return result;
	}
	
	//获取块高
	@ResponseBody
	@RequestMapping(value="/getblockcount",method=RequestMethod.POST)
	public JSONObject getBlockCount(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		
		WwBitcoinClient client=WwBitcoinClient.inst();
		
		int blockCount=client.getBlockCount();
				
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "成功");	
		result.put("blockCount", blockCount);
		
		
		return result;
	}
	
	//注册码
	@ResponseBody
	@RequestMapping(value="/getphonevalidcode_reg")
	public JSONObject getPhoneValidCode_reg(String phoneNum,HttpServletRequest request,HttpServletResponse response){
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
		
		String phoneValidCode=SmsUtil_heng.getNewVerifyNum();
		boolean b=SmsUtil_heng.sendVerifyNum_Reg(phoneNum, phoneValidCode);
		if(!b){
			result.put("success", false);	
			result.put("code", 1);
			result.put("message", "发送短信失败:"+SmsUtil_heng.message);
			
			return result;
		}
		
		ApiLoginContext.setPhoneValidCode(phoneNum, phoneValidCode,times);

		
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "短信已发送");	
		
		return result;
	}
	
	//身份验证验证码
	@ResponseBody
	@RequestMapping(value="/getphonevalidcode_id")
	public JSONObject getPhoneValidCode_id(String phoneNum,HttpServletRequest request,HttpServletResponse response){
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
		
		String phoneValidCode=SmsUtil_heng.getNewVerifyNum();
		boolean b=SmsUtil_heng.sendVerifyNum_ID(phoneNum, phoneValidCode);
		if(!b){
			result.put("success", false);	
			result.put("code", 1);
			result.put("message", "发送短信失败:"+SmsUtil_heng.message);
			
			return result;
		}
		
		ApiLoginContext.setPhoneValidCode(phoneNum, phoneValidCode,times);

		
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "短信已发送");	
		
		return result;
	}
	
	//登录确认验证码
	@ResponseBody
	@RequestMapping(value="/getphonevalidcode_login")
	public JSONObject getPhoneValidCode_login(String phoneNum,HttpServletRequest request,HttpServletResponse response){
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
		
		String phoneValidCode=SmsUtil_heng.getNewVerifyNum();
		boolean b=SmsUtil_heng.sendVerifyNum_Login(phoneNum, phoneValidCode);
		if(!b){
			result.put("success", false);	
			result.put("code", 1);
			result.put("message", "发送短信失败:"+SmsUtil_heng.message);
			
			return result;
		}
		
		ApiLoginContext.setPhoneValidCode(phoneNum, phoneValidCode,times);

		
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "短信已发送");	
		
		return result;
	}
	
	//修改密码验证码
	@ResponseBody
	@RequestMapping(value="/getphonevalidcode_chpw")
	public JSONObject getPhoneValidCode_chpw(String phoneNum,HttpServletRequest request,HttpServletResponse response){
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
		
		String phoneValidCode=SmsUtil_heng.getNewVerifyNum();
		boolean b=SmsUtil_heng.sendVerifyNum_ChangePW(phoneNum, phoneValidCode);
		if(!b){
			result.put("success", false);	
			result.put("code", 1);
			result.put("message", "发送短信失败:"+SmsUtil_heng.message);
			
			return result;
		}
		
		ApiLoginContext.setPhoneValidCode(phoneNum, phoneValidCode,times);

		
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "短信已发送");	
		
		return result;
	}
	
	//通用验证码
	@ResponseBody
	@RequestMapping(value="/getphonevalidcode_com")
	public JSONObject getPhoneValidCode_com(String phoneNum,
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
		
		String phoneValidCode=SmsUtil_heng.getNewVerifyNum();
		boolean b=SmsUtil_heng.sendVerifyNum_common(phoneNum, phoneValidCode);
		if(!b){
			result.put("success", false);	
			result.put("code", 1);
			result.put("message", "发送短信失败:"+SmsUtil_heng.message);
			
			return result;
		}
		
		ApiLoginContext.setPhoneValidCode(phoneNum, phoneValidCode,times);

		
		result.put("success", true);	
		result.put("code", 0);
		result.put("message", "短信已发送");	
		
		return result;
	}

}
