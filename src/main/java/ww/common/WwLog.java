package ww.common;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WwLog {

//	Logger log=LoggerFactory.getLogger(this.getClass());
//	//输出4种不同级别的日志
//    log.debug("111");
//    log.info("222");
//    log.warn("333");
//    log.error("444");
	
	public static Logger getLogger(Class<?> objClass){
		return LoggerFactory.getLogger(objClass);
	}
	
	public static Logger getLogger(Object ower){
		return LoggerFactory.getLogger(ower.getClass());
	}
	
	public static Logger getLogger(String name){
		return LoggerFactory.getLogger(name);
	}	
	
}
