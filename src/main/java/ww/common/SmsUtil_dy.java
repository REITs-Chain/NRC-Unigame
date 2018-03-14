package ww.common;

import java.util.Random;




import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SmsUtil_dy {
	public static String code="";
	public static String message="";
	
	public static String product="瑞资链";
	
	//身份验证验证码
	public static boolean sendVerifyNum_ID(String phoneNum,String verifyNum){
		return sendVerifyNum(phoneNum,verifyNum,"SMS_14275503","身份验证");
	}
	//登录确认验证码
	public static boolean sendVerifyNum_Login(String phoneNum,String verifyNum){
		return sendVerifyNum(phoneNum,verifyNum,"SMS_14275501","登陆验证");
	}
	//用户注册验证码
	public static boolean sendVerifyNum_Reg(String phoneNum,String verifyNum){
		return sendVerifyNum(phoneNum,verifyNum,"SMS_14275499","注册验证");
	}
	//修改密码验证码
	public static boolean sendVerifyNum_ChangePW(String phoneNum,String verifyNum){
		return sendVerifyNum(phoneNum,verifyNum,"SMS_14275497","变更验证");
	}
	//登录异常验证码
	public static boolean sendVerifyNum_LoginException(String phoneNum,String verifyNum){
		return sendVerifyNum(phoneNum,verifyNum,"SMS_14275500","登录验证");
	}
	
	public static boolean sendVerifyNum(String phoneNum,String verifyNum,String smsTemplateCode,String smsFreeSignName){
		String url="https://eco.taobao.com/router/rest";
		String appkey="23447106";
		String secret="9dfd8dafd7232a43749fb1af9c782b6f";
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType("normal");		
		req.setSmsParam("{\"code\":\""+verifyNum+"\",\"product\":\""+product+"\"}");
		req.setRecNum(phoneNum);
		req.setSmsFreeSignName(smsFreeSignName);
		req.setSmsTemplateCode(smsTemplateCode);
		try {
			AlibabaAliqinFcSmsNumSendResponse response = client.execute(req);
			if(response.isSuccess()){
				message="";
				return true;
			}
			String subCode=response.getSubCode();
			code=subCode;
			if(subCode.equals("isv.BUSINESS_LIMIT_CONTROL")){
				message="超过最大次数限制";
				return false;
			}else if(subCode.equals("isv.BUSINESS_LIMIT_CONTROL")){
				message="手机号码格式错误";
				return false;
			}else if(subCode.equals("isv.AMOUNT_NOT_ENOUGH")){
				message="余额不足";
				return false;
			}else{
				message="其他错误(错误代码："+subCode+")";
				return false;
			}
			
		} catch (ApiException e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
			Log log=LogFactory.getLog(SmsUtil_dy.class);
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
