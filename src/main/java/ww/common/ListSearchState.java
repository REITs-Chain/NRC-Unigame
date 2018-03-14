package ww.common;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class ListSearchState {
	
	private HttpServletRequest request = null;
	private String controllerName="";
	
	public ListSearchState(HttpServletRequest req,String controllerName){
		this.request=req;
		this.controllerName=controllerName;
	}
	
	public void saveStates(){
		//String mmm=this.request.getMethod();
		if(this.request.getMethod().toLowerCase().equals("post")){ //只保存post的值
			HashMap<String, String> states=getStates();
			if(states==null){
				states=new HashMap<String, String>();
				this.request.getSession().setAttribute(this.controllerName+"_states", states);
			}
			Enumeration<String> em = this.request.getParameterNames();
			while (em.hasMoreElements()) {
			    String name = (String) em.nextElement();
			    String value = this.request.getParameter(name);
			    states.put(name,value);
			}			
		}
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getStates(){
		return (HashMap<String, String>)this.request.getSession().getAttribute(this.controllerName+"_states");
	}
	
	/**
	 * 获取运行中当前状态的参数值
	 * @param paramererName
	 * @return
	 */
	public String getStateValue(String paramererName){
		String value=null;
		HashMap<String, String> states=getStates();
		if(states!=null){
			if(states.containsKey(paramererName)){
				value=states.get(paramererName);
			}
		}
		
		return value;
	}
	
	public void putStateValue(String paramererName,String value){
		HashMap<String, String> states=getStates();
		if(states==null){
			states=new HashMap<String, String>();
			this.request.getSession().setAttribute(this.controllerName+"_states", states);
		}
		states.put(paramererName, value);
	}
	
	/**
	 * 获取request中的post或get参数值，如果没有值则使用运行中当前状态的值
	 * @param paramererName
	 * @return
	 */	
	public String getParameterWithState(String paramererName){
		String value=this.request.getParameter(paramererName);	
		if(value==null){ //恢复状态
			HashMap<String, String> states=getStates();
			if(states!=null){
				if(states.containsKey(paramererName)){
					value=states.get(paramererName);
				}
			}
		}
		
		return value;
	}

}
