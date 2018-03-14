package ww.security;

import java.util.HashMap;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSONObject;

import ww.security.ConfigRule.RuleItem;
import ww.security.common.AES;
import ww.security.common.LoginEvent;
import ww.security.common.LoginInfo;
import ww.security.common.VerifyFilter;
import ww.security.common.VerifyResult;

public class SecurityManager implements InitializingBean {	
	
	private String loginUrl="";
	private String loginSuccessUrl="";
	private String unauthcUrl="";
	private boolean encryptPassword=false;	
	private ConfigRule configRule=null;	
	private DataRealm dataRealm=null;
	private LoginEvent loginEvent=null;
	private boolean useCook=true;
	
	private String filterName="";
	private HashMap<String, VerifyFilter> verifyFilters;

	public static HashMap<String,SecurityManager> securityManagerSet=new HashMap<String, SecurityManager>();

	public SecurityManager() {
		verifyFilters=new HashMap<String, VerifyFilter>(); 
		addDefaultVerifyFilter();
	}	
	
	/**
	 *返回值：0-验证成功  1-未登录(包括cookic) 2-没有权限 
	 * @param request
	 * @param response
	 * @return
	 */
	public VerifyResult doVerifyFilter(String URI,ServletRequest request, ServletResponse response){
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		HttpSession session=req.getSession();		
		
		session.setAttribute("wwsecurity_prev_uri", URI);
		if(configRule!=null&&configRule.ruleItems!=null&&configRule.ruleItems.size()>0){
			for(int i=0;i<configRule.ruleItems.size();i++){
				RuleItem ri=configRule.ruleItems.get(i);
				if(matches(URI,ri.getUriRegex())){
					if(ri.getValue().startsWith("anon")){
						return new VerifyResult(0,null);
					}else{
						for(String key:this.verifyFilters.keySet()){
							if(ri.getValue().startsWith(key)){						
								VerifyFilter vf=verifyFilters.get(key);
								VerifyResult vr=vf.doVerify(filterName, URI, ri, req, res,this);
								return vr;
							}
						}
					}
				}				
			}
		}		
		
		return new VerifyResult(1,null);////找不到过滤器视为未登录
	}
	
	public void setLoginCookie(LoginInfo li,HttpServletResponse response){
		//String v_str="{userName:'"+li.userName+"',password:'"+li.password+"'}";
		String v_str=JSONObject.toJSONString(li);
		String v_encrypt=AES.encrypt2(v_str, "!@#wesd%$RFgt_)Pl{+_}{:l{:l>,Lgr445");
		Cookie cookie = new Cookie("ww_rememberme",v_encrypt);
	    cookie.setPath("/"); //表示所有路径都可以访问
	    cookie.setMaxAge(60*60*24*4); //一秒为单位，60*60*24*3表示3天
	    response.addCookie(cookie);
	}
	
	public LoginInfo getLoginCookie(HttpServletRequest request){
		Cookie[] cookies=request.getCookies();
		if(cookies==null)
			return null;
		for(Cookie cookie:cookies){
			if(cookie.getName().equals("ww_rememberme")){
				String v_encrypt=cookie.getValue();
				String v_str=AES.decrypt2(v_encrypt, "!@#wesd%$RFgt_)Pl{+_}{:l{:l>,Lgr445");
				LoginInfo li=JSONObject.parseObject(v_str,LoginInfo.class);
				return li;
			}
		}
		return null;
	}
	
	public void removeLoginCookie(HttpServletRequest request,HttpServletResponse response){
		Cookie[] cookies=request.getCookies();
		if(cookies==null)
			return;
		for(Cookie cookie:cookies){
			if(cookie.getName().equals("ww_rememberme")){
				Cookie cookiedel = new Cookie("ww_rememberme",null);
				cookiedel.setPath("/"); //表示所有路径都可以访问
				cookiedel.setMaxAge(0); //删除
			    response.addCookie(cookiedel);
			}
		}
	}
	
	private void addDefaultVerifyFilter(){
		this.verifyFilters.put("login", new LoginVerifyFilter());
		this.verifyFilters.put("authc", new AuthcVerifyFilter());
	}
	
	public boolean matches(String uri,String rule_regex){
		if(uri.equals(rule_regex))
			return true;
		
		boolean flag=uri.matches(rule_regex);
		return flag;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {		
		
	}

	
	/*******属性*begin*******************************/

	public ConfigRule getConfigRule() {
		return configRule;
	}
	public void setConfigRule(ConfigRule configRule) {
		this.configRule = configRule;
	}


	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	
	public HashMap<String, VerifyFilter> getVerifyFilters() {
		return verifyFilters;
	}
	public void setVerifyFilters(HashMap<String, VerifyFilter> verifyFilters) {
		this.verifyFilters = verifyFilters;
		addDefaultVerifyFilter();
	}	

	public DataRealm getDataRealm() {
		return dataRealm;
	}


	public void setDataRealm(DataRealm dataRealm) {
		this.dataRealm = dataRealm;
	}


	public String getLoginUrl() {
		return loginUrl;
	}


	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}


	public String getLoginSuccessUrl() {
		return loginSuccessUrl;
	}


	public void setLoginSuccessUrl(String loginSuccessUrl) {
		this.loginSuccessUrl = loginSuccessUrl;
	}


	public String getUnauthcUrl() {
		return unauthcUrl;
	}


	public void setUnauthcUrl(String unauthcUrl) {
		this.unauthcUrl = unauthcUrl;
	}

	public LoginEvent getLoginEvent() {
		return loginEvent;
	}

	public void setLoginEvent(LoginEvent loginEvent) {
		this.loginEvent = loginEvent;
	}

	public boolean isEncryptPassword() {
		return encryptPassword;
	}

	public void setEncryptPassword(boolean encryptPassword) {
		this.encryptPassword = encryptPassword;
	}

	public boolean isUseCook() {
		return useCook;
	}

	public void setUseCook(boolean useCook) {
		this.useCook = useCook;
	}
	

}
