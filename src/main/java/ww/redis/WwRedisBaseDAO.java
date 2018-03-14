package ww.redis;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public abstract  class WwRedisBaseDAO<K extends Serializable, V extends Serializable> {
	
	  @Autowired
	  protected StringRedisTemplate redisTemplate;
	 
	  /**
	   * 设置redisTemplate
	   * @param redisTemplate the redisTemplate to set
	   */ 
	  public void setRedisTemplate(StringRedisTemplate redisTemplate) { 
	    this.redisTemplate = redisTemplate; 
	  } 
	     
	  /**
	   * 获取 RedisSerializer
	   * <br>------------------------------<br>
	   */ 
	  protected RedisSerializer<String> getStringSerializer() { 
	    return redisTemplate.getStringSerializer(); 
	  }

}
