package ww.common;

import java.util.Date;

public class WwVar{

	private Object rawValue=null;
	
	public WwVar(Object value) {
		this.rawValue=value;
	}
	
	public Object toObject(){
		return this.rawValue;
	}
	
	public int toInt(){
		return WwSystem.ToInt(this.rawValue);
	}
	public long toLong(){
		return WwSystem.ToLong(this.rawValue);
	}
	public double toDouble(){
		return WwSystem.ToDouble(this.rawValue);
	}
	public float toFloat(){
		return WwSystem.ToFloat(this.rawValue);
	}
	public boolean toBoolean(){
		return WwSystem.ToBoolean(this.rawValue);
	}
	public Date toDate(){
		if(this.rawValue instanceof Date)
			return (Date)this.rawValue;
			
		return WwSystem.ToDate(this.rawValue.toString());
	}
	public String toDateStr(){
		if(this.rawValue instanceof Date)					
			return WwSystem.ToDateStr((Date)this.rawValue);
		else if(this.rawValue instanceof String)
			return WwSystem.ToDateStr(WwSystem.ToDate((String)this.rawValue));
		else
			return "";
	}
	public String toDateTimeStr(){
		if(this.rawValue instanceof Date)					
			return WwSystem.ToDateTimeStr((Date)this.rawValue);
		else if(this.rawValue instanceof String)
			return WwSystem.ToDateTimeStr(WwSystem.ToDate((String)this.rawValue));
		else
			return "";
	}
	
	public String toString(){
		if(this.rawValue==null)
			return "";
		try{
			if(this.rawValue instanceof Integer){
				return this.rawValue.toString();
			}else if(this.rawValue instanceof Float){
				return this.rawValue.toString();
			}else if(this.rawValue instanceof Double){
				return this.rawValue.toString();
			}else if(this.rawValue instanceof Long){
				return this.rawValue.toString();
			}else if(this.rawValue instanceof String){
				return (String)this.rawValue;
			}else if(this.rawValue instanceof Date){
				return WwSystem.ToDateTimeStr((Date)this.rawValue);
			}else {
				return this.rawValue.toString();
			}		
		}catch(Exception e){
			return "";
		}	
	}

}
