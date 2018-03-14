package ww.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class WwJsonObject extends JSONObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4211454446391936089L;

	public WwJsonObject() {
		super();
	}
	
	public WwJsonObject putNewObject(String key){
		WwJsonObject obj=new WwJsonObject();
		super.put(key, obj);
		return obj;
	}
	
	public WwJsonObject put(String key,WwJsonObject obj){
		super.put(key, obj);
		return obj;
	}
	
	public WwJsonArray putNewArray(String key){
		WwJsonArray obj=new WwJsonArray();
		super.put(key, obj);
		return obj;
	}
	
	
	

}
