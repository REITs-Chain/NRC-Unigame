package ww.cache;

import org.springframework.beans.factory.InitializingBean;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class WwCacheManagerFactoryBean implements InitializingBean {
	
	private String configLocation="ehcache.xml";
	
	private CacheManager cacheManager=null;
	
	public CacheManager getCacheManager(){
		return cacheManager;
	}
	
	public Cache getFreeCache(){
		return cacheManager.getCache("FreeCache");
	}

	//sping在设置完属性值后执行此函数
	@Override
	public void afterPropertiesSet() throws Exception {
		this.cacheManager=CacheManager.create(configLocation);
		
	}

	public String getConfigLocation() {
		return configLocation;
	}

	public void setConfigLocation(String configLocation) {
		this.configLocation = configLocation;
	}
	

}
