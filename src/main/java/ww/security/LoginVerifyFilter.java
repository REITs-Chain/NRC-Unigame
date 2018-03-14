package ww.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ww.security.ConfigRule.RuleItem;
import ww.security.common.LoginInfo;
import ww.security.common.VerifyFilter;
import ww.security.common.VerifyResult;

public class LoginVerifyFilter implements VerifyFilter {

	public LoginVerifyFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public VerifyResult doVerify(String filterName,String URI, RuleItem ri, 
			HttpServletRequest request, HttpServletResponse response,SecurityManager securityManager) {
		
		HttpSession session=request.getSession();
		String loginUser=(String)session.getAttribute(filterName+"_loginUser");
		if(loginUser!=null){//已经登录
			VerifyResult vr=new VerifyResult();
			vr.status=0;
			vr.loginUser=loginUser;			
			return vr;
		}
		
		if(!securityManager.isUseCook()){ //是否使用cook
			System.out.println("未登录");			
			if(securityManager.getLoginEvent()!=null){
				securityManager.getLoginEvent().onAfterFaied(null, filterName, request, response, securityManager);
			}			
			return new VerifyResult(1,null);
		}
		
		//验证cook登录
		VerifyResult vr=doCookieLogin(filterName,request, response, securityManager);
		
		return vr;
	}
	
	//用cook登录
	public VerifyResult doCookieLogin(String filterName,HttpServletRequest request, 
			HttpServletResponse response,SecurityManager securityManager){
		
		HttpSession session=request.getSession();
		
		LoginInfo li_cook=securityManager.getLoginCookie(request);
		if(li_cook!=null){
			DataRealm dr=securityManager.getDataRealm();
			LoginInfo li_db=dr.getLoginInfo(li_cook.userName);
			if(li_db==null||li_db.userName==null){
				System.out.println("Cookie中的用户"+li_cook.userName+"不存在");
				
				if(securityManager.getLoginEvent()!=null){
					securityManager.getLoginEvent().onAfterFaied(li_db, filterName, request, response, securityManager);
				}
				
				return new VerifyResult(1,null);
			}
			if(li_cook.password.equals(li_db.password)){
				//登录成功
				session.setAttribute(securityManager.getFilterName()+"_loginUser", li_db.userName);
    			securityManager.setLoginCookie(li_db, response);
    			
    			if(securityManager.getLoginEvent()!=null){
    				securityManager.getLoginEvent().onAfterSuccess(li_db, filterName, request, response, securityManager);
    			}
    			
				return new VerifyResult(0,li_db.userName);
			}else{
				System.out.println("Cookie中的密码不正确");
				
				if(securityManager.getLoginEvent()!=null){
					securityManager.getLoginEvent().onAfterFaied(li_db, filterName, request, response, securityManager);
				}
				
				return new VerifyResult(1,null);
			}			
		}
		
		return new VerifyResult(1,null);
		
	}

}
