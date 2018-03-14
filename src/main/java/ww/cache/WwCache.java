package ww.cache;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class WwCache {
	
	private String classpath=null;
	
	private CacheManager cacheManager;
	private Cache cache;	
	
	public WwCache() {
		getClasspath();
		this.cacheManager=CacheManager.create(classpath+"ehcache.xml");
		this.cache=this.cacheManager.getCache("ww_defaultCache");
	}
	
	public WwCache(String cachName) {
		getClasspath();
		this.cacheManager=CacheManager.create(classpath+"ehcache.xml");
		this.cache=this.cacheManager.getCache(cachName);
	}
	
	protected void getClasspath() {
		if(classpath==null){
			classpath=this.getClass().getResource("/").getPath();
			try {
				classpath=URLDecoder.decode(classpath,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Cache getCache(){
		return this.cache;
	}
	
	public boolean remove(String key){
		return this.cache.remove(key);
	}
	
	public void removeAll(){
		this.cache.removeAll();
	}
	
	public boolean isExist(String key){
		if(this.cache.get(key)==null)
			return false;
		
		return true;
	}
	
	public void setObject(String key,Object value){
		Element el=new Element(key, value);
		this.cache.put(el);
	}	
	
	/**
	 * 带空闲时间和有效时间，时间单位：秒
	 * @param key
	 * @param value
	 * @param timeToIdleSeconds 带空闲时间
	 * @param timeToLiveSeconds 有效时间
	 */
	public void setObject(String key,Object value,Integer timeToIdleSeconds,Integer timeToLiveSeconds){
		Element el=new Element(key, value, false, timeToIdleSeconds, timeToLiveSeconds);
		this.cache.put(el);
	}	
	public Object getObject(String key){
		Element el=this.cache.get(key);
		if(el!=null){
			return el.getObjectValue();
		}else{
			return null;
		}
	}
	
	public void setString(String key,String value){
		Element el=new Element(key, value);
		this.cache.put(el);
	}
	public String getString(String key){
		Element el=this.cache.get(key);
		
		if(el!=null){
			return (String)el.getObjectValue();
		}else{
			return "";
		}
	}
	
	public void setInteger(String key,Integer value){
		Element el=new Element(key, value);
		this.cache.put(el);
	}
	public Integer getInteger(String key){
		Element el=this.cache.get(key);
		
		if(el!=null){
			return (Integer)el.getObjectValue();
		}else{
			return 0;
		}
	}
	
	public void setLong(String key,Long value){
		Element el=new Element(key, value);
		this.cache.put(el);		
	}
	public Long getLong(String key){
		Element el=this.cache.get(key);
		
		if(el!=null){
			return (Long)el.getObjectValue();
		}else{
			return 0L;
		}
	}
	
	public void setDouble(String key,Double value){
		Element el=new Element(key, value);
		this.cache.put(el);
	}
	public Double getDouble(String key){
		Element el=this.cache.get(key);
		
		if(el!=null){
			return (Double)el.getObjectValue();
		}else{
			return 0.0;
		}
	}

}
