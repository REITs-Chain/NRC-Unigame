package ww.common;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;

public class SqlList extends ArrayList<SqlMap> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3127769056892726290L;
	
	public SqlMap find(String fieldname,Object value){
		if(value==null)
			return null;
		for(int i=0;i<this.size();i++){
			SqlMap im=this.get(i);
			if(im.containsKey(fieldname)&&im.get(fieldname)!=null&&im.get(fieldname).toString().equalsIgnoreCase(value.toString())){
				return im;
			}
		}
		
		return null;
	}
	
	public JSONArray toJSONArray(){
		JSONArray list=new JSONArray();
		for(SqlMap sm:this){
			list.add(sm.toJSONObject());
		}
		return list;
	}

}
