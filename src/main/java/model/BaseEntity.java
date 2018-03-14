package model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ww.common.DbModel;
import ww.common.SqlMap;
import ww.common.WwSystem;
import ww.common.WwVar;

public class BaseEntity {

	public long getDays(Date begin,Date end){
		long diff = end.getTime() - begin.getTime();//这样得到的差值是微秒级别
		long days = diff / (1000 * 60 * 60 * 24);
		
		return days;
	}
	
	public Date getNow(){
		return WwSystem.now();
	}
	public Date Now(){
		return WwSystem.now();
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
	
	public boolean hasProperty(String proName){
		Field[] fields=this.getClass().getDeclaredFields();	
		for (int i = 0; i < fields.length; i++) {	
			Field f=fields[i];
			if(f.getName().equals(proName)){	
				return true;
			}		
		}
		return false;
	}
	
	public void setPropertyValue(String proName,Object value){
		try {
			Field[] fields=this.getClass().getDeclaredFields();	
			for (int i = 0; i < fields.length; i++) {	
				Field f=fields[i];
				if(f.getName().equals(proName)){				
					f.setAccessible(true);
					f.set(this, value);				
					return;
				}		
			}
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public Object getPropertyValue(String proName){
		try {
			Field[] fields=this.getClass().getDeclaredFields();	
			for (int i = 0; i < fields.length; i++) {	
				Field f=fields[i];
				if(f.getName().equals(proName)){
					f.setAccessible(true);
					return f.get(this);
				}		
			}		
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
	
	public String getPropertyType(String proName){
		try {
			Field[] fields=this.getClass().getDeclaredFields();	
			for (int i = 0; i < fields.length; i++) {	
				Field f=fields[i];
				if(f.getName().equals(proName)){
					f.setAccessible(true);
					return f.getType().toString();
				}		
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	//克隆属性
	public void mergeProperty(BaseEntity objInst,HttpServletRequest request){
		if(objInst==null)
			return;
		//合并对象实列的属性
	    Enumeration<String> paramNames = request.getParameterNames();
	    while (paramNames.hasMoreElements()) {
	    	String paramName = (String) paramNames.nextElement();
	    	if(this.hasProperty(paramName)){
	    		this.setPropertyValue(paramName, objInst.getPropertyValue(paramName));
	    	}
	    }
	}
	
	/**
	 * 获取多(childs)对一(parent)中一个父表记录对象,myField：本对象中的字段名它对应父表主键
	 * @param parentClass
	 * @param myField  本对象中的字段名它对应父表主键
	 * @return
	 */
	public <T> T _getParent(String parentClass,String myField){
		Class<T> c;
		try {
			c = (Class<T>)Class.forName(parentClass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return _getParent(c,myField);
	}
	
	/**
	 * 获取多(childs)对一(parent)中一个父表记录对象,myField：本对象中的字段名它对应父表主键
	 * @param parentClass
	 * @param myField 本对象中的字段名它对应父表主键
	 * @return
	 */
	public <T> T _getParent(Class<T> parentClass,String myField){
		DbModel dao=new DbModel();
		if(parentClass==null)
			return null;
		try{
			Field ff=parentClass.getDeclaredField("_keyName");
			ff.setAccessible(true);
			String my_keyName=(String)ff.get(null);	//静态变量值		
			if(my_keyName==null||my_keyName.isEmpty()){
				return null;
			}	
			
			Field f=parentClass.getDeclaredField("_tableName");
			f.setAccessible(true);
			String parent_tableName=(String)f.get(null); //静态变量值
			if(parent_tableName==null||parent_tableName.isEmpty()){
				return null;
			}			
			
			Object value=this.getPropertyValue(myField);
			if(value==null){
				return null;
			}
			
			List<T> list=dao.table(parent_tableName).where(my_keyName+"="+value.toString()).select(parentClass);
			if(list.size()>0)
				return list.get(0);
			else
				return null;			
		}catch(Exception e){
			return null;
		}	
	}
	
	/**
	 * 获取多(childs)对一(parent)中一个父表记录的指定字段值,myField：本对象中的字段名它对应父表主键(主键默认为id)
	 * @param parentClass
	 * @param myField 本对象中的字段名它对应父表主键
	 * @return
	 */
	public WwVar _getParentField(String myField,String parentTable,String parentField){
		return _getParentField(myField,parentTable,"id",parentField);
	}
	
	/**
	 * 获取多(childs)对一(parent)中一个父表记录的指定字段值,myField：本对象中的字段名它对应父表主键(主键默认为id)
	 * @param parentClass
	 * @param myField 本对象中的字段名它对应父表主键
	 * @return
	 */
	public WwVar _getParentField(String myField,String parentTable,String parentKey,String parentField){
		DbModel dao=new DbModel();
		if(parentTable==null || parentTable.isEmpty())
			return null;
		try{
			if(parentKey.isEmpty())
				parentKey="id";
			
			Object value=this.getPropertyValue(myField);
			if(value==null){
				return null;
			}
			
			SqlMap data=dao.table(parentTable).where(parentKey+"=:id").bind("id", value).selectOne(parentField);
			if(data==null)
				return null;	
			
			return new WwVar(data.get(parentField));
		}catch(Exception e){
			return null;
		}	
	}
	
	/**
	 * 获取多(childs)对一(parent)中多开个子表记录对象list,childField：子表中的字段名它对应本对象中的主键
	 * @param childClass
	 * @param childField：子表中的字段名它对应本对象中的主键
	 * @return
	 */
	public <T> List<T> _getChilds(String childClass,String childField){
		Class<T> c;
		try {
			c = (Class<T>)Class.forName(childClass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return _getChilds(c,childField);
	}
	
	/**
	 * 获取多(childs)对一(parent)中多开个子表记录对象list,childField：子表中的字段名它对应本对象中的主键
	 * @param childClass
	 * @param childField：子表中的字段名它对应本对象中的主键
	 * @return
	 */
	public <T> List<T> _getChilds(Class<T> childClass,String childField){
		DbModel dao=new DbModel();
		if(childClass==null)
			return new ArrayList<T>();
		try{
			Field ff=this.getClass().getDeclaredField("_keyName");
			ff.setAccessible(true);
			String my_keyName=(String)ff.get(null);	//静态变量值		
			if(my_keyName==null||my_keyName.isEmpty()){
				return new ArrayList<T>();
			}			
			Object value=this.getPropertyValue(my_keyName);
			if(value==null){
				return new ArrayList<T>();
			}
			
			Field f=childClass.getDeclaredField("_tableName");
			f.setAccessible(true);
			String child_tableName=(String)f.get(null); //静态变量值
			if(child_tableName==null||child_tableName.isEmpty()){
				return new ArrayList<T>();
			}
			
			List<T> list=dao.table(child_tableName).where(childField+"="+value.toString()).select(childClass);
			return list;			
		}catch(Exception e){
			return new ArrayList<T>();
		}	
	}
	
	public String getEnumName(String enumClass, String proName){
		Object value=this.getPropertyValue(proName);
		int v=0;
		if(value!=null)
			v=(int)value;
		
		if(enumClass.indexOf(".")<0){
			enumClass="model.common."+enumClass;
		}		
		
		Method method;
		Object res="";
		try {
			Class<?> cls=Class.forName(enumClass);
			method = cls.getMethod("getEnumName",Integer.class);
			res = method.invoke(null,v);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (String)res;
		
	}
	
	
}
