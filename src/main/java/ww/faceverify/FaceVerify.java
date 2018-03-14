package ww.faceverify;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.entity.StringEntity;

import com.alibaba.fastjson.JSONObject;

import ww.common.ModelDAO;
import ww.common.SqlMap;

public class FaceVerify {
	String url="https://118.178.93.185:4003/api/recognition/v2";
	//String url="http://118.178.93.185:80/api/recognition/v2";
	String api_key="de7005e6-6c74-11e7-8f8b-acbc32c784b5";
	String api_secret="3270e962be60083cc860042112e5947a13b1668afa70f9c21e850c7ec434e9b2";
	
	public String saveError="";

	public FaceVerify() {
		// TODO Auto-generated constructor stub
	}
	
	//身份证照片识别
	public JSONObject ocrIdcard(String trans_id,String trade_date, String idcardImage){
		try{
			trans_id+="_ocr";
	        // 请求的接口类型
	        String verify_type = "ocr_id_name_check_v2";
	
	        Map<String,Object> dataMap = new HashMap<String, Object>();
	        // 设置时间戳
	        String timestamp = Long.toString(System.currentTimeMillis());
	        dataMap.put("timestamp", timestamp);
	        // 设置api_key
	        dataMap.put("api_key", api_key);
	        // 设置请求接口类型
	        dataMap.put("verify_type", verify_type);
	        // 生成订单标识, 唯一
	        dataMap.put("trans_id", trans_id);
	        // 创建时间 年月日时分秒 例: 20170213195356        
	        dataMap.put("trade_date", trade_date);
	
	        // 传入的param附带的参数
	        Map<String,String> paramMap = new HashMap<String, String>();
	        paramMap.put("image", idcardImage);
	
	        dataMap.put("param", paramMap);
	
	        String sign = createSign(api_key, api_secret, timestamp);
	        JSONObject jsonData=new JSONObject();
	        for(Object k:dataMap.keySet()){
	            if((String)k=="param"){
	                JSONObject paramJsonData=new JSONObject();
	                for(Object param:paramMap.keySet()){
	                    paramJsonData.put((String)param, paramMap.get(param));
	                }
	                jsonData.put("param", paramJsonData);
	            }else{
	                jsonData.put((String)k, dataMap.get(k));
	            }
	        }
	
	        jsonData.put("sign", sign);
	        System.out.println("发送的响应内容"+jsonData.toString());
	
	        String response = sendPost(url, jsonData.toString());
	        System.out.println("接收到的响应内容:" + response);
	        
	        JSONObject obj=JSONObject.parseObject(response);
	        if(obj.getBoolean("success")){
	        	JSONObject data=obj.getJSONObject("data");
	        	int code=data.getInteger("code");
				String desc=data.getString("desc");
				JSONObject extra=data.getJSONObject("extra");
				
				JSONObject result=new JSONObject();
				result.put("code", code);
				result.put("message", desc);
				result.put("success", code==1);
				result.put("extra", extra);
				return result;
	        }else{
	        	JSONObject result=new JSONObject();
				result.put("success", false);	
				result.put("code", -1);
				result.put("message", "异常:"+response);
				return result;
	        }
		}catch(Exception e){
			JSONObject result=new JSONObject();
			result.put("success", false);	
			result.put("code", -1);
			result.put("message", "异常:"+e.getMessage());
			return result;
		}
        
    }
	
	//活体识别(身份证和人脸对比)
	public JSONObject faceToIdcard(String trans_id,String trade_date,String manImage,String idcardImage){
		try{
	        // 请求的接口类型
	        String verify_type = "one2one_face_compare_v2";
	
	        Map<String,Object> dataMap = new HashMap<String, Object>();
	        // 设置时间戳
	        String timestamp = Long.toString(System.currentTimeMillis());
	        dataMap.put("timestamp", timestamp);
	        // 设置api_key
	        dataMap.put("api_key", api_key);
	        // 设置请求接口类型
	        dataMap.put("verify_type", verify_type);
	        // 生成订单标识, 唯一
	        dataMap.put("trans_id", trans_id);
	        // 创建时间 年月日时分秒 例: 20170213195356        
	        dataMap.put("trade_date", trade_date);
	
	        // 传入的param附带的参数
	        Map<String,String> paramMap = new HashMap<String, String>();
	        paramMap.put("image1", manImage);
	        paramMap.put("image2", idcardImage);
	
	        dataMap.put("param", paramMap);
	
	        String sign = createSign(api_key, api_secret, timestamp);
	        JSONObject jsonData=new JSONObject();
	        for(Object k:dataMap.keySet()){
	            if((String)k=="param"){
	                JSONObject paramJsonData=new JSONObject();
	                for(Object param:paramMap.keySet()){
	                    paramJsonData.put((String)param, paramMap.get(param));
	                }
	                jsonData.put("param", paramJsonData);
	            }else{
	                jsonData.put((String)k, dataMap.get(k));
	            }
	        }
	
	        jsonData.put("sign", sign);
	        System.out.println("发送的响应内容"+jsonData.toString());
	
	        String response = sendPost(url, jsonData.toString());
	        System.out.println("接收到的响应内容:" + response);
	        
	        JSONObject obj=JSONObject.parseObject(response);
	        if(obj.getBoolean("success")){
	        	JSONObject data=obj.getJSONObject("data");
	        	int code=data.getInteger("code");
				String desc=data.getString("desc");
				JSONObject extra=data.getJSONObject("extra");
				String score_str=extra.getString("score");
				double score=0;
				if(score_str!=null&&!score_str.isEmpty()){
					score=Double.parseDouble(score_str);
				}
				
				JSONObject result=new JSONObject();
				result.put("code", code);
				result.put("message", desc);
				result.put("success", code==1 && score>69.9);
				result.put("extra", extra);
				return result;
	        }else{
	        	JSONObject result=new JSONObject();
				result.put("success", false);	
				result.put("code", -1);
				result.put("message", "异常1:"+response);
				return result;
	        }
		}catch(Exception e){
			JSONObject result=new JSONObject();
			result.put("success", false);
			result.put("code", -1);
			result.put("message", "异常2:"+e.getMessage());
			return result;
		}
        
    }
	
	/**
     * 进行签名
     * @param api_key
     * @param api_secret
     * @param timestamp
     * @return
     */
    public static String createSign(String api_key, String api_secret, String timestamp) {
        String sign = "";

        try {
            MessageDigest md5=MessageDigest.getInstance("MD5");
            byte[] content = (api_key + api_secret + timestamp).getBytes();
            md5.update(content);
            byte[] resultByteArray = md5.digest();
            sign = bytesToHexString(resultByteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("签名:" + sign);
        return sign;
    }

    /**
     * 发送post 请求
     * @param url
     * @param param
     * @return
     */
    public static String sendPost(String url, String param) {
        StringEntity entity = null;
        try {
            entity = new StringEntity(param,"utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
        } catch (Exception e ){
            e.printStackTrace();
        }
        return new HttpClientUtil().doPost(url,entity,"utf-8");
    }

    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    
    public boolean saveImages(int userId,String transId,String tradeDate,
    		String manImage,String idcardImage){
    	
    	ModelDAO dao=new ModelDAO();
    	dao.M("t_user_verify").where("userId="+userId);    	
    	SqlMap sm=new SqlMap();
    	sm.put("transId", transId);
    	sm.put("tradeDate", tradeDate);
    	sm.put("manImage", manImage);
    	sm.put("idcardImage", idcardImage);
    	
    	long res=0;
    	if(dao.select("id").size()<=0){
    		sm.put("userId", userId);
    		res=dao.insert(sm);
    	}else{
    		res=dao.update(sm);
    	}
    	
    	this.saveError=dao.Message;
    			
    	if(res==-1){
    		return false;
    	}else{
    		return true;
    	}    	
    	
    }
    
    /**
     * status:状态{type:'选择框',options:[{value:0,text:'未认证'},{value:1,text:'身份证识别成功'},{value:2,text:'活体识别成功'},{value:-1,text:'身份证识别失败'},{value:-2,text:'活体识别失败'}]}
     * @param userId
     * @param ocrResult
     * @param status
     * @return
     */
    public boolean saveOcrResult(int userId,String ocrResult,int status){
    	
    	ModelDAO dao=new ModelDAO();
    	dao.M("t_user_verify").where("userId="+userId);    	
    	SqlMap sm=new SqlMap();
    	sm.put("ocrResult", dao.rmSpecChars(ocrResult));
    	sm.put("status", status);
    	
    	long res=dao.update(sm);
    	
    	this.saveError=dao.Message;
    			
    	if(res==-1){
    		return false;
    	}else{
    		return true;
    	}    	
    }
    
    /**
     * status:状态{type:'选择框',options:[{value:0,text:'未认证'},{value:1,text:'身份证识别成功'},{value:2,text:'活体识别成功'},{value:-1,text:'身份证识别失败'},{value:-2,text:'活体识别失败'}]}
     * @param userId
     * @param result
     * @param status
     * @return
     */
    public boolean saveFaceResult(int userId,String result,int status){
    	
    	ModelDAO dao=new ModelDAO();
    	dao.M("t_user_verify").where("userId="+userId);    	
    	SqlMap sm=new SqlMap();
    	sm.put("result", dao.rmSpecChars(result));
    	sm.put("status", status);
    	
    	long res=dao.update(sm);
    	
    	this.saveError=dao.Message;
    			
    	if(res==-1){
    		return false;
    	}else{
    		return true;
    	}    	
    }

}
