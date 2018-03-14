package ww.common;

import com.alibaba.fastjson.JSONArray;

public class WwJsonArray extends JSONArray {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2919166410907409911L;

	public WwJsonArray() {
		super();
	}
	
	public WwJsonObject add(WwJsonObject obj){
		super.add(obj);
		return obj;
	}
	
	public WwJsonObject addNewObject(){
		WwJsonObject obj=new WwJsonObject();
		super.add(obj);
		return obj;
	}

}
