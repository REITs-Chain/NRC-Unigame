package ww.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class WwSystem {
	
	public static String UUID(){
		return java.util.UUID.randomUUID().toString();
	}
	
	/**
	 * 返回项目的相对url：项目在ROOT时返回"/"，项目为非ROOT时返回"/项目文件夹名称/"
	 * @param request
	 * @return
	 */
	public static String getRoot(HttpServletRequest request){
		String path = request.getContextPath();
		String basePath = path+"/";
		return basePath;
	}
	
	/**
	 * 返回项目的绝对url：项目在ROOT时返回"http://localhost:8080/"，项目为非ROOT时返回"http://localhost:8080/项目文件夹名称/"
	 * @param request
	 * @return
	 */
	public static String getFullRoot(HttpServletRequest request){
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		return basePath;
	}
	
	/**
	 * 获取web项目根文件路径，/结尾
	 * @return
	 */
	public static String getRootPath(){
		String rootPath=WwSystem.class.getResource("/../../").getFile().toString();
		rootPath=rootPath.replaceAll("%20", " ");
		return rootPath;
	}
	
	/**
	 * 去掉毫秒
	 * @return
	 */
	public static java.util.Date now(){
		long cur=java.lang.System.currentTimeMillis();
        Date now=new Date(cur-cur%1000);
        return now;
	}
	
	/**
	 * 包含毫秒
	 * @return
	 */
	public static java.util.Date now_full(){
		Calendar now = Calendar.getInstance(); 
        return now.getTime();
	}	
	
	public static int getYear(java.util.Date date){
		Calendar now = Calendar.getInstance(); 
		now.setTime(date);
        return now.get(Calendar.YEAR);
	}
	
	public static int getMonth(java.util.Date date){
		Calendar now = Calendar.getInstance(); 
		now.setTime(date);
        return now.get(Calendar.MONTH)+1;
	}
	
	public static int getDay(java.util.Date date){
		Calendar now = Calendar.getInstance(); 
		now.setTime(date);
        return now.get(Calendar.DAY_OF_MONTH);
	}
	
	public static int getHour(java.util.Date date){
		Calendar now = Calendar.getInstance(); 
		now.setTime(date);
        return now.get(Calendar.HOUR_OF_DAY);
	}
	
	public static int getMinute(java.util.Date date){
		Calendar now = Calendar.getInstance(); 
		now.setTime(date);
        return now.get(Calendar.MINUTE);
	}
	
	/**
	 * 获取星期几
	 * @param date
	 * @return
	 */
	public static int getWeekDay(java.util.Date date){
		Calendar now = Calendar.getInstance(); 
		now.setTime(date);
        return now.get(Calendar.DAY_OF_WEEK);
	}
	
	
	/**
	 * 获取秒部分
	 * @param date
	 * @return
	 */
	public static int getSecond(java.util.Date date){
		Calendar now = Calendar.getInstance(); 
		now.setTime(date);
        return now.get(Calendar.SECOND);
	}
	
	public static java.util.Date addDays(Date date,int days){
		if(date==null)
			return null;
		
		Calendar calendar = new GregorianCalendar(); 
	    calendar.setTime(date); 
	    
	    calendar.add(Calendar.DATE,days);
	    
	    Date newdate=calendar.getTime();
	    return newdate;
	}
	
	public static java.util.Date addMonths(Date date,int months){
		if(date==null)
			return null;
		
		Calendar calendar = new GregorianCalendar(); 
	    calendar.setTime(date); 
	    
	    calendar.add(Calendar.MONTH, months);
	    
	    Date newdate=calendar.getTime();
	    return newdate;
	}
	
	public static java.util.Date addYears(Date date,int years){
		if(date==null)
			return null;
		
		Calendar calendar = new GregorianCalendar(); 
	    calendar.setTime(date); 
	    
	    calendar.add(Calendar.YEAR, years);
	    
	    Date newdate=calendar.getTime();
	    return newdate;
	}
	
	public static String ToString(Object value){
		if(value==null)
			return "";
		try{
			return value.toString();			
		}catch(Exception e){
			return "";
		}	
	}
	
	/**
	 * 将整数转换为指定长度的字符串
	 * @param value
	 * @param len
	 * @return
	 */
	public static String ToString(int value,int len){		
		// 0 代表前面补充0   
	    // 10代表长度为10   
	    // d 代表参数为正数型   
	    String str = String.format("%0"+len+"d", value); 
	    return str;
	}
	/**
	 * 将整数转换为指定长度的字符串
	 * @param value
	 * @param len
	 * @return
	 */
	public static String ToString(long value,int len){		
		// 0 代表前面补充0   
	    // 10代表长度为10   
	    // d 代表参数为正数型   
	    String str = String.format("%0"+len+"d", value); 
	    return str;
	}
	
	public static int ToInt(Object value){
		if(value==null)
			return 0;
		try{
			if(value instanceof Integer){
				return ((Integer)value).intValue();
			}else if(value instanceof Float){
				return ((Float)value).intValue();
			}else if(value instanceof Double){
				return ((Double)value).intValue();
			}else if(value instanceof Long){
				return ((Long)value).intValue();
			}else if(value instanceof String){
				try{
					return Integer.parseInt((String)value);
				}catch(Exception e2){
					return Double.valueOf((String)value).intValue();
				}
			}else {
				return Double.valueOf(value.toString()).intValue();
			}
		}catch(Exception e){
			return 0;
		}	
	}
	
	public static long ToLong(Object value){
		if(value==null)
			return 0;
		try{
			if(value instanceof Integer){
				return ((Integer)value).longValue();
			}else if(value instanceof Float){
				return ((Float)value).longValue();
			}else if(value instanceof Double){
				return ((Double)value).longValue();
			}else if(value instanceof Long){
				return ((Long)value).longValue();
			}else if(value instanceof String){
				try{
					return Long.parseLong((String)value);
				}catch(Exception e2){
					return Double.valueOf((String)value).longValue();
				}
			}else {
				return Double.valueOf(value.toString()).longValue();
			}
		}catch(Exception e){
			return 0;
		}	
	}
	
	public static double ToDouble(Object value){
		if(value==null)
			return 0;
		try{
			if(value instanceof Integer){
				return ((Integer)value).doubleValue();
			}else if(value instanceof Float){
				return ((Float)value).doubleValue();
			}else if(value instanceof Double){
				return ((Double)value).doubleValue();
			}else if(value instanceof Long){
				return ((Long)value).doubleValue();
			}else if(value instanceof String){
				return Double.parseDouble(value.toString());
			}else {
				return Double.valueOf(value.toString());
			}	
		}catch(Exception e){
			return 0;
		}		
	}
	
	public static float ToFloat(Object value){
		if(value==null)
			return 0;
		try{
			if(value instanceof Integer){
				return ((Integer)value).floatValue();
			}else if(value instanceof Float){
				return ((Float)value).floatValue();
			}else if(value instanceof Double){
				return ((Double)value).floatValue();
			}else if(value instanceof Long){
				return ((Long)value).floatValue();
			}else if(value instanceof String){
				return Float.parseFloat(value.toString());
			}else {
				return Float.valueOf(value.toString());
			}		
		}catch(Exception e){
			return 0;
		}		
	}
	
	public static Date ToDate(String date_str){
		if(date_str==null||date_str.isEmpty()||date_str.trim().equalsIgnoreCase(""))
			return null;
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(date_str);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
	}
	
	public static boolean IsDate(String date_str){
		if(date_str==null||date_str.isEmpty()||date_str.trim().equalsIgnoreCase(""))
			return false;
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(date_str);
			return true;
		} catch (ParseException e) {
			return false;
		}		
	}
	
	public static Date ToDateTime(String date_str){
		if(date_str==null||date_str.isEmpty()||date_str.trim().equalsIgnoreCase(""))
			return null;
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date_str);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
	}
	
	public static boolean IsDateTime(String date_str){
		if(date_str==null||date_str.isEmpty()||date_str.trim().equalsIgnoreCase(""))
			return false;
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date_str);
			return true;
		} catch (ParseException e) {
			return false;
		}		
	}
	
	public static String ToDateStr(Date date){
		if(date==null)
			return "";
		String str = new SimpleDateFormat("yyyy-MM-dd").format(date);
		return str;
	}
	
	public static String ToDateTimeStr(Date date){
		if(date==null)
			return "";
		String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		return str;
	}
	
	public static String ToDateTimeStr(Date date,String format){
		if(date==null)
			return "";
		if(format.isEmpty()||format.equals(""))
			format="yyyy-MM-dd HH:mm:ss";
		String str = new SimpleDateFormat(format).format(date);
		return str;
	}
	
	public static Boolean ToBoolean(Object value){
		if(value==null)
			return false;
		
		String vv=value.toString();
		
		if(vv.equalsIgnoreCase("true"))
			return true;	
		
		if(vv.equalsIgnoreCase("1"))
			return true;		
		
		if(vv.equalsIgnoreCase("-1"))
			return true;
		
		if(vv.equals(""))
			return false;

		return Boolean.parseBoolean(value.toString());
	}
	
	public static long dateToStamp(String date_str) throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(date_str);
        long ts = date.getTime();
        long res = ts/1000;
        return res;
    }
	
	public static long dateToStamp(Date date) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
        long ts = simpleDateFormat.parse(simpleDateFormat.format(date)).getTime();
        long res = ts/1000;
        return res;
    }
	
	public static Date stampToDate(long s){
        long lt = s*1000;
        Date date = new Date(lt);
        return date;
    }
	
	public static String stampToDateStr(long s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = s*1000;
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
	
	public static String InputStreamToString(InputStream is){
		if(is==null)
			return "";		
		
		try {
			StringBuilder stringBuilder = new StringBuilder();
	        BufferedReader bufferedReader;
			bufferedReader = new BufferedReader(new InputStreamReader(is,"utf8"));
		
	        String line = null; 
	        while((line = bufferedReader.readLine()) != null){         
	          stringBuilder.append(System.getProperty("line.separator"));        
	          stringBuilder.append(line); 
	        } 
	        return stringBuilder.toString();
	        
		} catch (UnsupportedEncodingException e) {
			return "";
		} catch (IOException e) {
			return "";
		}
	}
	
	public static boolean isNumeric(String str){ 
	   Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]*"); 
	   Matcher isNum = pattern.matcher(str);
	   if( !isNum.matches() ){
	       return false; 
	   } 
	   return true; 
	}
	
	public static boolean isInteger(String str){ 
		   Pattern pattern = Pattern.compile("-?[0-9]+"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	
	public static String getSystemVar(String varName){
		ModelDAO dao=new ModelDAO();
		SqlMap sm=dao.M("w_system").where("name='"+varName+"'").selectOne("value");
		if(sm==null)
			return "";
		return sm.getString("value");
	}
	
}
