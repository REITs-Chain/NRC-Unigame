package ww.cache;

public class WwSessionCache extends WwCache {
	
	private static WwSessionCache _instance=null;	

	public WwSessionCache() {
		super("session_cache");
	}
	
	public static WwSessionCache getInstance(){
		if(_instance==null)
			_instance=new WwSessionCache();
		
		return _instance;
	}

}
