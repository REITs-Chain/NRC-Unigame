package ww.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.Function;

public class WwFunctionMenu {
	
	public Function fun;
	public Map<Integer,WwFunctionMenu> submenus;
	
	public WwFunctionMenu(){
		submenus=new LinkedHashMap<Integer,WwFunctionMenu>();
	}
	
	public WwFunctionMenu findSubMenu(String url){
		if(url==null||url.isEmpty())
			return null;
		Iterator iter = submenus.entrySet().iterator();
    	while(iter.hasNext()){
    		Map.Entry entry = (Map.Entry) iter.next();
            WwFunctionMenu menu = (WwFunctionMenu)entry.getValue();
            if(menu.fun.getFurl().isEmpty())
            	continue;
            if(url.endsWith(menu.fun.getFurl().toLowerCase()))
            	return menu;
    	}
    	return null;
	}

	public static Map<Integer,WwFunctionMenu> getFunctions(){		
		ModelDAO dao=new ModelDAO();		
		List<Function> funs=dao.M("w_function").where("fvisible=1").orderBy("fnumber").select(Function.class);
		
		LinkedHashMap<Integer,WwFunctionMenu> menus=new LinkedHashMap<Integer,WwFunctionMenu>();
		
		for(int i=0;i<funs.size();i++){
			Function fun=funs.get(i);
			WwFunctionMenu menu=new WwFunctionMenu();
			menu.fun=fun;
			if(fun.getFlevel()==1){				
				menus.put(fun.getFid(), menu);
			}else{
				if(menus.containsKey(fun.getFparentId())){
					menus.get(fun.getFparentId()).submenus.put(fun.getFid(), menu);
				}
			}
		}		
		
		return menus;
	}
	
}
