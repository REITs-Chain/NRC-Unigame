package ww.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

public class WwRedisDAO2 extends WwRedisBaseDAO2<String, Serializable> {
	
	public boolean isExpire=true; //是否启用有效时长
	public long expire=1800;//有效时长30分钟（单秒）
	
	private static WwRedisDAO2 _inst=null;

	public WwRedisDAO2() {
		
	}
	
	public static WwRedisDAO2 inst(){
		if(_inst==null)
			_inst=new WwRedisDAO2();
		
		return _inst;
	}
	
	public Boolean set(String key,byte[] value){
		if(value==null)
			return false;
		Boolean res=redisTemplate.execute(new RedisCallback<Boolean>() { 
			public Boolean doInRedis(RedisConnection connection) 
					throws DataAccessException { 
				RedisSerializer<String> serializer = getStringSerializer(); 
		        byte[] _key  = serializer.serialize(key);
		        if(isExpire){
		        	connection.setEx(_key, expire, value); 
		        }else{
		        	connection.set(_key, value);		        	
		        }
		        return true;
			} 
		});	
		return res;
	}
	
	/**
	 * 带有效时长
	 * @param key
	 * @param exp
	 * @param value
	 * @return
	 */
	public Boolean setEx(String key,byte[] value){
		if(value==null)
			return false;
		Boolean res=redisTemplate.execute(new RedisCallback<Boolean>() { 
			public Boolean doInRedis(RedisConnection connection) 
					throws DataAccessException { 
				RedisSerializer<String> serializer = getStringSerializer(); 
		        byte[] _key  = serializer.serialize(key);
		        connection.setEx(_key, expire, value); 
		        
		        return true;
			} 
		});	
		return res;
	}
	
	public boolean set(String key,Object value){
		if(value==null)
			return false;
		byte[] _val=SerializeUtil.serialize(value);
		return set(key,_val);		
	}
	
	public boolean set(String key,String value){		
		byte[] _val=getStringSerializer().serialize(value);
		return set(key,_val);		
	}
	
	public boolean set(String key,int value){
		String val=String.valueOf(value);
		return set(key,val);		
	}
	
	public boolean set(String key,long value){
		String val=String.valueOf(value);
		return set(key,val);		
	}
	
	public boolean set(String key,float value){
		String val=String.valueOf(value);
		return set(key,val);
	}
	
	public boolean set(String key,double value){
		String val=String.valueOf(value);
		return set(key,val);		
	}
	
	public boolean set(String key,boolean value){
		String val=String.valueOf(value);
		return set(key,val);	
	}
	
	public void delete(List<String> keys) { 
	    redisTemplate.delete(keys); 
	}
	
	public void delete(String key) { 
	    List<String> list = new ArrayList<String>(); 
	    list.add(key); 
	    delete(list); 
	}
	
	public byte[] get(final String key) {
		
		byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() { 
		    public byte[] doInRedis(RedisConnection connection) 
		          throws DataAccessException { 
		        RedisSerializer<String> serializer = getStringSerializer(); 
		        byte[] _key = serializer.serialize(key); 
		        byte[] _value = connection.get(_key); 
		        if (_value == null) { 
		          return null; 
		        } 
		        return _value; 
	        } 
	    }); 
	    return result; 
	}
	
	public String getString(final String key){
		byte[] val=get(key);
		if(val==null)
			return null;
		return getStringSerializer().deserialize(val);
	}
	
	public int getInteger(final String key){
		byte[] val=get(key);
		if(val==null)
			return 0;
		String value=getStringSerializer().deserialize(val);
		return Integer.valueOf(value);
	}
	
	public long getLong(final String key){
		byte[] val=get(key);
		if(val==null)
			return 0;
		String value=getStringSerializer().deserialize(val);
		return Long.valueOf(value);
	}
	
	public float getFloat(final String key){
		byte[] val=get(key);
		if(val==null)
			return 0;
		String value=getStringSerializer().deserialize(val);
		return Float.valueOf(value);
	}
	
	public double getDouble(final String key){
		byte[] val=get(key);
		if(val==null)
			return 0;
		String value=getStringSerializer().deserialize(val);
		return Double.valueOf(value);
	}
	
	public Boolean getBoolean(final String key){
		byte[] val=get(key);
		if(val==null)
			return null;
		String value=getStringSerializer().deserialize(val);
		return Boolean.valueOf(value);
	}
	
	public Object getObject(final String key) {		
		byte[] val=get(key);
		if(val==null)
			return null;

		return SerializeUtil.unserialize(val); 
	}

}
