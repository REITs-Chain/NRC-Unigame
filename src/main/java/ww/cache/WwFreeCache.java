package ww.cache;

public class WwFreeCache extends WwCache {
	
	private static WwFreeCache _instance=null;	

	public WwFreeCache() {
		super("FreeCache");
	}
	
	public static WwFreeCache getInstance(){
		if(_instance==null)
			_instance=new WwFreeCache();
		
		return _instance;
	}

}
