package ww.idcard;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;

import com.alibaba.fastjson.JSONObject;
//实名认证
public class IDCard {
	private final static String mall_id="110615";
	private final static String appkey="2e0f54b4b96b8660ccc604d625d75012";
	
	
	public JSONObject ResultJson=null;
	
	public String message="";
	
	/**
	 * 身份证认证
	 * @param realname
	 * @param idcard
	 * @return
	 */
	public int idcard_verify(String realname,String idcard){
		String url="http://121.41.42.121:8080/v2/id-server?";
		
		idcard=idcard.toLowerCase();
		long tm=System.currentTimeMillis();
		String md5_param=mall_id+realname+idcard+tm+appkey;
		String sign=md5(md5_param);
		String param=new StringBuffer().append("mall_id="+mall_id)
				.append("&realname="+realname)
				.append("&idcard="+idcard)
				
				.append("&tm="+tm)
				.append("&sign="+sign).toString();
		String url_v=url+param;
		try {
			url_v=url_v.replace(realname,URLEncoder.encode(realname,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonString=url2string(url_v);
		
		JSONObject result=JSONObject.parseObject(jsonString);
		ResultJson=result;
		System.out.println(result);
		int status=Integer.parseInt(result.getString("status"));
		int code=Integer.parseInt(result.getJSONObject("data").getString("code"));
		message=result.getJSONObject("data").getString("message");
		
		/*客户可以根据自己的业务需求进行处理*/
		if(status==2001){
			//2001=正常服务			
			if(code==1000){
				message="一致";				
			}else if(code==1001){
				message="不一致";					
			}else if(code==1002){
				message="库中无此号";					
			}
			//如果命令相应正常，一下情况不需要处理			
			else if(code==1101){
				message="商家ID不合法";				
			}else if(code==1102){
				message="身份证姓名不合法";				
			}else if(code==1103){
				message="身份证号码不合法";
				
			}else if(code==1104){
				message="签名不合法";
				
			}else if(code==1107){
				message="tm不合法";				
			}				
		}
		//正常情况下不需要处理，商家也可以根据自己的业务进行处理
		else if(status==2002){
			message="第三方服务器异常";			
		}else if(status==2003){
			message="服务器维护";			
		}else if(status==2004){
			message="账号余额不足";			
		}else if(status==2005){
			message="参数异常";			
		}

		//1000=一致
		//1001=不一致
		//1002=库中无此号
		//1101=商家ID不合法
		//1102=身份证姓名不合法
		//1103=身份证号码不合法
		//1104=签名不合法
		//1105=第三方服务器异常
		//1106=账户余额不足
		//1107=tm不合法
		//1108=其他异常
		//1109=账号被暂停

		return code;
	}
	
	/**
	 * 银行卡认证
	 * @param realname
	 * @param bankcard
	 * @return
	 */
	public int bankcard_verify(String realname,String bankcard,String idcard){
		//String url="http://121.41.42.121:8080/v3/card3-server?";
		String url="http://121.41.42.121:8080/v3/card2-server?"; 
		
		bankcard=bankcard.toLowerCase();
		long tm=System.currentTimeMillis();
		String md5_param=mall_id+realname+bankcard+tm+appkey;
		String sign=md5(md5_param);
		String param=new StringBuffer().append("mall_id="+mall_id)
				.append("&realname="+realname)
				.append("&cardnum="+bankcard)
				.append("&idcard="+idcard)
				.append("&tm="+tm)
				.append("&sign="+sign).toString();
		String url_v=url+param;
		try {
			url_v=url_v.replace(realname,URLEncoder.encode(realname,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonString=url2string(url_v);
		
		JSONObject result=JSONObject.parseObject(jsonString);
		ResultJson=result;
		System.out.println(result);
		int status=Integer.parseInt(result.getString("status"));
		int code=Integer.parseInt(result.getJSONObject("data").getString("code"));
		message=result.getJSONObject("data").getString("message");
		
		/*客户可以根据自己的业务需求进行处理*/
		if(status==2001){
			//2001=正常服务			
			if(code==1000){
				message="验证成功";				
			}else if(code==1001){
				message="卡号姓名不匹配";					
			}else if(code==1002){
				message="无法认证";					
			}
			//如果命令相应正常，一下情况不需要处理			
			else if(code==1101){
				message="商家ID不合法";				
			}else if(code==1102){
				message="姓名不合法";				
			}else if(code==1103){
				message="银行卡号不合法 ";
				
			}else if(code==1104){
				message="签名不合法";
				
			}else if(code==1107){
				message="tm不合法";				
			}				
		}
		//正常情况下不需要处理，商家也可以根据自己的业务进行处理
		else if(status==2002){
			message="第三方服务器异常";			
		}else if(status==2003){
			message="服务器维护";			
		}else if(status==2004){
			message="账号余额不足";			
		}else if(status==2005){
			message="参数异常";			
		}

		//1000=一致
		//1001=不一致
		//1002=库中无此号
		//1101=商家ID不合法
		//1102=身份证姓名不合法
		//1103=身份证号码不合法
		//1104=签名不合法
		//1105=第三方服务器异常
		//1106=账户余额不足
		//1107=tm不合法
		//1108=其他异常
		//1109=账号被暂停

		return code;
	}
	
	/**
	 * 获取银行卡信息
	 * @param bankcard
	 * @return
	 */
	public JSONObject getBankcardInfo(String bankcard){
		String url="http://121.41.42.121:8080/bankcard/city-server?"; 
		
		bankcard=bankcard.toLowerCase();
		long tm=System.currentTimeMillis();
		String md5_param=mall_id+bankcard+tm+appkey;
		String sign=md5(md5_param);
		String param=new StringBuffer().append("mall_id="+mall_id)
				.append("&bankcardno="+bankcard)				
				.append("&tm="+tm)
				.append("&sign="+sign).toString();
		String url_v=url+param;
		String jsonString=url2string(url_v);
		
		JSONObject result=JSONObject.parseObject(jsonString);
		ResultJson=new JSONObject();
		//System.out.println(result);		
		int code=Integer.parseInt(result.getJSONObject("data").getString("code"));
		message=result.getJSONObject("data").getString("message");
		if(code==1000){
			ResultJson.put("success", true);
			ResultJson.put("message", message);
			ResultJson.put("data", result.getJSONObject("status"));
			return ResultJson;
		}
		
		ResultJson=new JSONObject();
		ResultJson.put("success", false);
		ResultJson.put("message", message);
		return ResultJson;
	}
	
	public int phone_verify(String realname,String idcard,String phone){
		String url="http://120.27.32.132:8080/phone/server?";	    
		
		idcard=idcard.toLowerCase();
		long tm=System.currentTimeMillis();
		String md5_param=mall_id+realname+idcard+tm+appkey;
		String sign=md5(md5_param);
		String param=new StringBuffer().append("mall_id="+mall_id)
				.append("&realname="+realname)
				.append("&idcard="+idcard)
				.append("&phone="+phone)
				.append("&tm="+tm)
				.append("&sign="+sign).toString();
		String url_v=url+param;
		try {
			url_v=url_v.replace(realname,URLEncoder.encode(realname,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonString=url2string(url_v);
		
		JSONObject result=JSONObject.parseObject(jsonString);
		ResultJson=result;
		System.out.println(result);
		int status=Integer.parseInt(result.getString("status"));
		int code=Integer.parseInt(result.getJSONObject("data").getString("code"));
		message=result.getJSONObject("data").getString("message");
		
		/*客户可以根据自己的业务需求进行处理*/
		if(status==2001){
			//2001=正常服务			
			if(code==1000){
				message="一致";				
			}else if(code==1001){
				message="不一致";					
			}else if(code==1002){
				message="库中无此号";					
			}
			//如果命令相应正常，一下情况不需要处理			
			else if(code==1101){
				message="商家ID不合法";				
			}else if(code==1102){
				message="身份证姓名不合法";				
			}else if(code==1103){
				message="身份证号码不合法";
				
			}else if(code==1104){
				message="签名不合法";
				
			}else if(code==1107){
				message="tm不合法";				
			}				
		}
		//正常情况下不需要处理，商家也可以根据自己的业务进行处理
		else if(status==2002){
			message="第三方服务器异常";			
		}else if(status==2003){
			message="服务器维护";			
		}else if(status==2004){
			message="账号余额不足";			
		}else if(status==2005){
			message="参数异常";			
		}

		//1000=一致
		//1001=不一致
		//1002=库中无此号
		//1101=商家ID不合法
		//1102=身份证姓名不合法
		//1103=身份证号码不合法
		//1104=签名不合法
		//1105=第三方服务器异常
		//1106=账户余额不足
		//1107=tm不合法
		//1108=其他异常
		//1109=账号被暂停

		return code;
	}	
	
	private String md5(String s){
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes("utf-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
	private String url2string(String url){
		StringBuffer sb=new StringBuffer();
		try {
			InputStream is=new URL(url).openStream();
			byte[] buf=new byte[1024*10];
			int len=0;
			while((len=is.read(buf, 0, 1024*10))>0){
				sb.append(new String(buf,0,len,"UTF-8"));
			}
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}



}
