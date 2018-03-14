package ww.common;

import java.util.Random;




import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SmsUtil_heng {
	public static String url="http://api.hengtongexun.com:8090/protocol/sendMessage.do";
	public static String userid="100838";
	public static String password="A8471713797E70B71500620D765D208F";//MD5加密，32位大写格式//Ruizkj0710
	public static String codetype="utf8";
	public static String encodeway="base64";
	
	public static String message="";//错误信息
	
	public static String product="NRC社区";
	
	//发送通知
	public static boolean sendNotice(String phoneNum,String notice){
		String msg="【"+product+"通知】"+notice;
		return sendVerifyNum(phoneNum,msg);
	}
	
	//通用验证码
	public static boolean sendVerifyNum_common(String phoneNum,String verifyNum){
		String msg="【"+product+"】验证码为："+verifyNum+"，打死都不能说！";
		return sendVerifyNum(phoneNum,msg);
	}
	
	//身份验证验证码
	public static boolean sendVerifyNum_ID(String phoneNum,String verifyNum){
		String msg="【"+product+"】身份验证码为："+verifyNum+"，打死都不能说！";
		return sendVerifyNum(phoneNum,msg);
	}
	//登录确认验证码
	public static boolean sendVerifyNum_Login(String phoneNum,String verifyNum){
		String msg="【"+product+"】登录验证码为："+verifyNum+"，打死都不能说！";
		return sendVerifyNum(phoneNum,msg);
	}
	//用户注册验证码
	public static boolean sendVerifyNum_Reg(String phoneNum,String verifyNum){
		String msg="【"+product+"】注册验证码为："+verifyNum+"，打死都不能说！";
		return sendVerifyNum(phoneNum,msg);
	}
	//修改密码验证码
	public static boolean sendVerifyNum_ChangePW(String phoneNum,String verifyNum){
		String msg="【"+product+"】变更验证码为："+verifyNum+"，打死都不能说！";
		return sendVerifyNum(phoneNum,msg);
	}
	
	
	public static boolean sendVerifyNum(String phone,String msg){
		String msg_base64=WwBase64.encode(msg);
		try {
			WwHttpRequest req=new WwHttpRequest();
			String params="userid="+userid;
			params += "&password="+password;
			params += "&phone="+phone;
			params += "&codetype="+codetype;
			params += "&encodeway="+encodeway;
			params += "&message="+msg_base64;
			String res=req.post(url, params);

			long subCode=Long.parseLong(res);
			if(subCode>0){
				return true;
			}else if(subCode==-2){
				message="参数错误";
				return false;
			}else if(subCode==-3){
				message="账号密码错误";
				return false;
			}else if(subCode==-4){
				message="余额不足";
				return false;
			}else if(subCode==-5){
				message="参数格式错误";
				return false;
			}else if(subCode==-6){
				message="内容超长";
				return false;
			}else{
				message="其他错误";
				return false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
			Log log=LogFactory.getLog(SmsUtil_heng.class);
			log.equals("发短信异常："+e.getMessage());
			
			return false;
		}
	}
	
	public static String getNewVerifyNum(){
	  int num=6;//验证码长度
	  Random random=new Random();
	  //初始化种子
	  String[] str={"0","1","2","3","4","5","6","7","8","9"};
				   //,"a","b","c","d","e","f","g","h","i","j",
				   //"k","l","m","n","p","q","r","s","t"};
	  int number=str.length;
	  //接收随机字符
	  String text = "";
	  //随机产生4个字符的字符串
	  for(int i=0;i<num;i++){		  
	   text+=str[random.nextInt(number)];
	  }
	  return text;
	}

}
