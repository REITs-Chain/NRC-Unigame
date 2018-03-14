package ww.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class SqlMap extends HashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3584071015315826119L;

	@Override
	public Object get(Object key) {
		if(containsKey(key))
			return super.get(key);
		else
			return null;
	}	
	
	public Object get(String key) {
		if(containsKey(key))
			return super.get(key);
		else
			return null;
	}
	
	public WwVar getToVar(Object key) {
		if(containsKey(key))
			return new WwVar(super.get(key));
		else
			return null;
	}
	
	public int getInt(String key) {
		if(containsKey(key))
			return WwSystem.ToInt(get(key));
		else
			return 0;
	}
	
	public long getLong(String key) {
		if(containsKey(key))
			return WwSystem.ToLong(get(key));
		else
			return 0;
	}
	
	public double getDouble(String key) {
		if(containsKey(key))
			return WwSystem.ToDouble(get(key));
		else
			return 0;
	}
	
	public float getFloat(String key) {
		if(containsKey(key))
			return WwSystem.ToFloat(get(key));
		else
			return 0;
	}
	
	public boolean getBoolean(String key) {
		if(containsKey(key))
			return WwSystem.ToBoolean(get(key));
		else
			return false;
	}
	
	public String getString(String key) {
		if(containsKey(key))
			return WwSystem.ToString(get(key));
		else
			return "";
	}
	
	public Date getDate(String key){
		if(containsKey(key))
			if(get(key)==null)
				return null;
			else
				return (Date)get(key);
		else
			return null;
	}
	
	public String getDateStr(String key){
		if(containsKey(key))
			if(get(key)==null)
				return "";
			else
				return WwSystem.ToDateStr((Date)get(key));
		else
			return "";
	}
	
	public String getDateTimeStr(String key){
		if(containsKey(key))
			if(get(key)==null)
				return "";
			else
				return WwSystem.ToDateTimeStr((Date)get(key));
		else
			return "";
	}
	
	public String subString(String value, long begin,long end){
		if(value==null)
			return "";
		if(value.isEmpty()||value.equals(""))
			return "";
		
		if(value.length()<=end+1)
			return value;
		
		return value.substring((int)begin,(int)end);
	}
	
	public SqlMap getJoinOne(String toTable,String toField,String myField){
		Object value=this.get(myField);
		if(value==null)
			return null;
		DbModel dao=new DbModel();
		return dao.table(toTable).where(toField+"='"+value.toString()+"'").selectOne();
	}
	
	public SqlList getJoinList(String toTable,String toField,String myField){
		Object value=this.get(myField);
		if(value==null)
			return null;
		DbModel dao=new DbModel();
		return dao.table(toTable).where(toField+"='"+value.toString()+"'").select();
	}
	
	public String toJson(){
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		
		Iterator iter = this.entrySet().iterator();
		int bb=0;
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next(); 
			Object key = entry.getKey();			
			Object val = entry.getValue();
			String val_pat="";
			if(val==null){
				val_pat="\"\"";
			}else if(val instanceof String){
				val_pat="\""+val.toString()+"\"";
			}else if(val instanceof Integer){
				val_pat=val.toString();
			}else if(val instanceof Long){
				val_pat=val.toString();
			}else if(val instanceof Double){
				val_pat=val.toString();
			}else if(val instanceof Float){
				val_pat=val.toString();
			}else if(val instanceof Boolean){
				val_pat=val.toString();
			}else if(val instanceof Date){
				val_pat="\""+WwSystem.ToDateTimeStr((Date)val)+"\"";
			}else{
				val_pat="\""+val.toString()+"\"";
			}
			if(bb!=0){				
				sb.append(",");
			}
			sb.append("\""+key+"\":"+val_pat);
			bb++;
		}	
		sb.append("}");
		return sb.toString();
	}
	
	public String toSqlString(String join_char){
		if(join_char==null||join_char.isEmpty())
			join_char="and";
		StringBuffer sb=new StringBuffer();
		
		Iterator iter = this.entrySet().iterator();
		int bb=0;
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next(); 
			Object key = entry.getKey();			
			Object val = entry.getValue();
			String val_pat="";
			if(val==null){
				val_pat="''";
			}else if(val instanceof String){
				val_pat="'"+val.toString()+"'";
			}else if(val instanceof Integer){
				val_pat=val.toString();
			}else if(val instanceof Long){
				val_pat=val.toString();
			}else if(val instanceof Double){
				val_pat=val.toString();
			}else if(val instanceof Float){
				val_pat=val.toString();
			}else if(val instanceof Boolean){
				val_pat=val.toString();
			}else if(val instanceof Date){
				val_pat="'"+WwSystem.ToDateTimeStr((Date)val)+"'";
			}else{
				val_pat="'"+val.toString()+"'";
			}
			if(bb!=0){				
				sb.append(" "+join_char+" ");
			}
			sb.append(key+"="+val_pat);
			bb++;
		}
		return sb.toString();
	}
	
	public JSONObject toJSONObject(){
		JSONObject json=new JSONObject();
						
		Iterator iter = this.entrySet().iterator();
		int bb=0;
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next(); 
			Object key = entry.getKey();			
			Object val = entry.getValue();
			json.put(key.toString(), val);		
		}
		
		return json;
	}
	
}
