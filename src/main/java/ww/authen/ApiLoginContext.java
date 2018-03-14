package ww.authen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.User;
import ww.cache.WwSessionCache;
import ww.redis.WwRedisDAO2;

public class ApiLoginContext {
	public static class PhoneValidCodeInfo implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -5163834213694835843L;
		public String ValidCode="";
		public Date CreateDate;
		public int Times=0; //次数
		
		public PhoneValidCodeInfo(){
			
		}
	}
	public static class ImageValidCodeInfo implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -4334904287042907673L;
		public String ValidCode="";
		public Date CreateDate;
		
		public ImageValidCodeInfo(){
			
		}
	}
	
	public static boolean isRedis=false;
	
	/**
	 * 
	 * @param user
	 * @param token
	 */
	public static void setToken(LoginUser user,String token){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String tokenMap_key="tokenMap_"+user.getUsername();
			redis.set(tokenMap_key, token);
			
			String loginUserMap_key="loginUserMap_"+token;
			redis.set(loginUserMap_key, user);		
			
		}else{
			String tokenMap_key="tokenMap_"+user.getUsername();
			WwSessionCache.getInstance().setString(tokenMap_key, token);
			
			String loginUserMap_key="loginUserMap_"+token;
			WwSessionCache.getInstance().setObject(loginUserMap_key, user);		
		}
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public static String getToken(String username){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			String tokenMap_key="tokenMap_"+username;
			return redis.getString(tokenMap_key);			
		}else{		
			String tokenMap_key="tokenMap_"+username;
			return WwSessionCache.getInstance().getString(tokenMap_key);
		}
		
	}
	
	
	public static void setUser(String token,LoginUser user){		
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String loginUserMap_key="loginUserMap_"+token;
			redis.set(loginUserMap_key, user);
			String tokenMap_key="tokenMap_"+user.getUsername();
			redis.set(tokenMap_key, token);
			
		}else{				
			String loginUserMap_key="loginUserMap_"+token;
			WwSessionCache.getInstance().setObject(loginUserMap_key, user);
			String tokenMap_key="tokenMap_"+user.getUsername();
			WwSessionCache.getInstance().setObject(tokenMap_key, token);
		}
	}
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	public static LoginUser getUser(String token){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String loginUserMap_key="loginUserMap_"+token;
			LoginUser lu=(LoginUser)redis.getObject(loginUserMap_key);
			return lu;
		}else{
		
			String loginUserMap_key="loginUserMap_"+token;
			LoginUser lu=(LoginUser)WwSessionCache.getInstance().getObject(loginUserMap_key);
			return lu;
		}
	}
	
	
	
	/**
	 * 
	 * @param userName
	 */
	public static void removeUser(String userName){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String tokenMap_key="tokenMap_"+userName;
			String token_old=redis.getString(tokenMap_key);
			if(!token_old.equals("")&&!token_old.isEmpty()){
				String loginUserMap_key="loginUserMap_"+token_old;
				redis.delete(loginUserMap_key);
			}
			redis.delete(tokenMap_key);
		}else{		
			String tokenMap_key="tokenMap_"+userName;
			String token_old=WwSessionCache.getInstance().getString(tokenMap_key);
			if(!token_old.equals("")&&!token_old.isEmpty()){
				String loginUserMap_key="loginUserMap_"+token_old;
				WwSessionCache.getInstance().remove(loginUserMap_key);
			}
			WwSessionCache.getInstance().remove(tokenMap_key);
		}
	}
	
	/**
	 * 
	 * @param token
	 */
	public static void removeToken(String token){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String loginUserMap_key="loginUserMap_"+token;
			LoginUser lu=(LoginUser)redis.getObject(loginUserMap_key);
			if(lu!=null){
				String tokenMap_key="tokenMap_"+lu.getUsername();
				redis.delete(tokenMap_key);
			}
			WwSessionCache.getInstance().remove(loginUserMap_key);	
		}else{
			String loginUserMap_key="loginUserMap_"+token;
			LoginUser lu=(LoginUser)WwSessionCache.getInstance().getObject(loginUserMap_key);
			if(lu!=null){
				String tokenMap_key="tokenMap_"+lu.getUsername();
				WwSessionCache.getInstance().remove(tokenMap_key);
			}
			WwSessionCache.getInstance().remove(loginUserMap_key);		
		}
	}
	
	/**
	 * 
	 * @param phoneNum
	 * @param validCode
	 */
	public static void setPhoneValidCode(String phoneNum,String validCode){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String PhoneValidCodeMap_key="PhoneValidCodeMap_"+phoneNum;
			redis.delete(PhoneValidCodeMap_key);
			PhoneValidCodeInfo pvci=new PhoneValidCodeInfo();
			pvci.ValidCode=validCode;
			pvci.CreateDate=new Date();
			redis.set(PhoneValidCodeMap_key, pvci);		
		}else{
			String PhoneValidCodeMap_key="PhoneValidCodeMap_"+phoneNum;
			WwSessionCache.getInstance().remove(PhoneValidCodeMap_key);
			PhoneValidCodeInfo pvci=new PhoneValidCodeInfo();
			pvci.ValidCode=validCode;
			pvci.CreateDate=new Date();
			WwSessionCache.getInstance().setObject(PhoneValidCodeMap_key, pvci);
		}
	}
	
	/**
	 * 
	 * @param phoneNum
	 * @param validCode
	 * @param times 调用次数
	 */
	public static void setPhoneValidCode(String phoneNum,String validCode,int times){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
		
			String PhoneValidCodeMap_key="PhoneValidCodeMap_"+phoneNum;
			redis.delete(PhoneValidCodeMap_key);
			PhoneValidCodeInfo pvci=new PhoneValidCodeInfo();
			pvci.ValidCode=validCode;
			pvci.CreateDate=new Date();
			pvci.Times=times;
			redis.set(PhoneValidCodeMap_key, pvci);
		}else{
			String PhoneValidCodeMap_key="PhoneValidCodeMap_"+phoneNum;
			WwSessionCache.getInstance().remove(PhoneValidCodeMap_key);
			PhoneValidCodeInfo pvci=new PhoneValidCodeInfo();
			pvci.ValidCode=validCode;
			pvci.CreateDate=new Date();
			pvci.Times=times;
			WwSessionCache.getInstance().setObject(PhoneValidCodeMap_key, pvci);
		}
	}
	
	/**
	 * 
	 * @param phoneNum
	 */
	public static PhoneValidCodeInfo getPhoneValidCode(String phoneNum){		
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String PhoneValidCodeMap_key="PhoneValidCodeMap_"+phoneNum;
			PhoneValidCodeInfo pvci=(PhoneValidCodeInfo)redis.getObject(PhoneValidCodeMap_key);
			return pvci;
		}else{
			String PhoneValidCodeMap_key="PhoneValidCodeMap_"+phoneNum;
			PhoneValidCodeInfo pvci=(PhoneValidCodeInfo)WwSessionCache.getInstance().getObject(PhoneValidCodeMap_key);
			return pvci;
		}
	}
	
	/**
	 * 
	 * @param phoneNum
	 */
	public static void removePhoneValidCode(String phoneNum){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String PhoneValidCodeMap_key="PhoneValidCodeMap_"+phoneNum;
			redis.delete(PhoneValidCodeMap_key);
		}else{
			String PhoneValidCodeMap_key="PhoneValidCodeMap_"+phoneNum;
			WwSessionCache.getInstance().remove(PhoneValidCodeMap_key);
		}
	}
	
		
	/**
	 * 
	 * @param token
	 * @param validCode
	 */
	public static void setImageValidCode(String token,String validCode){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String ImageValidCodeMap_key="ImageValidCodeMap_"+token;
			redis.delete(ImageValidCodeMap_key);
			ImageValidCodeInfo pvci=new ImageValidCodeInfo();
			pvci.ValidCode=validCode;
			pvci.CreateDate=new Date();
			redis.set(ImageValidCodeMap_key, pvci);
		}else{
			String ImageValidCodeMap_key="ImageValidCodeMap_"+token;
			WwSessionCache.getInstance().remove(ImageValidCodeMap_key);
			ImageValidCodeInfo pvci=new ImageValidCodeInfo();
			pvci.ValidCode=validCode;
			pvci.CreateDate=new Date();
			WwSessionCache.getInstance().setObject(ImageValidCodeMap_key, pvci);
		}
	}
	/**
	 * 
	 * @param token
	 * @return
	 */
	public static ImageValidCodeInfo getImageValidCode(String token){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String ImageValidCodeMap_key="ImageValidCodeMap_"+token;
			ImageValidCodeInfo pvci=(ImageValidCodeInfo)redis.getObject(ImageValidCodeMap_key);
			return pvci;
		}else{
			String ImageValidCodeMap_key="ImageValidCodeMap_"+token;
			ImageValidCodeInfo pvci=(ImageValidCodeInfo)WwSessionCache.getInstance().getObject(ImageValidCodeMap_key);
			return pvci;
		}
	}
	
	/**
	 * 
	 * @param token
	 */
	public static void removeImageValidCode(String token){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String ImageValidCodeMap_key="ImageValidCodeMap_"+token;
			redis.delete(ImageValidCodeMap_key);
		}else{
			String ImageValidCodeMap_key="ImageValidCodeMap_"+token;
			WwSessionCache.getInstance().remove(ImageValidCodeMap_key);
		}
	}	
	

}
