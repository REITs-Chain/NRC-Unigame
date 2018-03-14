package ww.redis;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import ww.common.WwSpringContextHelper;

public class WwRedisBaseDAO2<K extends Serializable, V extends Serializable> {

	  protected RedisTemplate<K,V> redisTemplate;
	  
	  public WwRedisBaseDAO2(){
		  redisTemplate=WwSpringContextHelper.getBean("redisTemplate");//.getBean(org.springframework.jdbc.core.JdbcTemplate.class);			
	  }
	  
	  protected RedisSerializer<String> getStringSerializer() { 
		  return redisTemplate.getStringSerializer(); 
	  }

}
