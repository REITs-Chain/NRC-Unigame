package ww.security;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ww.security.common.AES;
import ww.security.common.LoginInfo;
import ww.security.common.MD5;

public class SecurityLogin {
	private String filterName="";
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private LoginInfo loginInfo=null;
	private int status=-1;	

	public SecurityLogin(String filter_name,HttpServletRequest request,HttpServletResponse response) {
		this.filterName=filter_name;
		this.request=request;
		this.response=response;
	}
	
	/**
	 * 
	 * @param userName
	 * @param password
	 * @param isMemberMe
	 * @param isMD5
	 * @return 0:登录成功  1:用户不正确   2：密码不正确
	 */
	public int doLoginIn(String userName,String password,boolean isRememberMe){
		HttpSession session=request.getSession();
		
		SecurityManager securityManager=SecurityManager.securityManagerSet.get(this.filterName);
		
		DataRealm dr=securityManager.getDataRealm();
		LoginInfo li=dr.getLoginInfo(userName);
		this.loginInfo=li;
		if(li==null||li.userName==null){
			System.out.println("用户"+userName+"不存在");
			this.status=1;
			
			if(securityManager.getLoginEvent()!=null){
				securityManager.getLoginEvent().onAfterFaied(li, filterName, request, response, securityManager);
			}
			
			return 1;
		}
		String inputpw=password;
		if(securityManager.isEncryptPassword()){
			inputpw=MD5.md5(password);
		}
		
		if(inputpw.equals(li.password)){//登录成功
			session.setAttribute(this.filterName+"_loginUser", li.userName);
			this.status=0;
			if(isRememberMe){
				securityManager.setLoginCookie(li, response);
			}
			
			if(securityManager.getLoginEvent()!=null){
				securityManager.getLoginEvent().onAfterSuccess(li, filterName, request, response, securityManager);
			}
			
			return 0;
		}else{
			System.out.println("密码不正确");
			this.status=2;
			
			if(securityManager.getLoginEvent()!=null){
				securityManager.getLoginEvent().onAfterFaied(li, filterName, request, response, securityManager);
			}
			
			return 2;
		}		
	}
	
	/**
	 * 登出
	 */
	public void doLoginOut(){
		HttpSession session=request.getSession();
		session.removeAttribute(this.filterName+"_loginUser");
		SecurityManager securityManager=SecurityManager.securityManagerSet.get(this.filterName);
		securityManager.removeLoginCookie(request, response);
	}
	
	public void RedirectSuccess(){		
		if(this.status==0){
			String cp=request.getContextPath();
			String hostPath="";
			if(!cp.equals("/")){
				hostPath=cp;
			}		
			try{
				String wwsecurity_prev_uri=(String)request.getSession().getAttribute("wwsecurity_prev_uri");
				if(wwsecurity_prev_uri!=null&&!wwsecurity_prev_uri.isEmpty()){
					response.sendRedirect(hostPath+wwsecurity_prev_uri);
					
				}else{
					SecurityManager securityManager=SecurityManager.securityManagerSet.get(this.filterName);		
					response.sendRedirect(hostPath+securityManager.getLoginSuccessUrl());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}		
	}
	public void RedirectSuccess(String successUrl){		
		if(this.status==0){
			String cp=request.getContextPath();
			String hostPath="";
			if(!cp.equals("/")){
				hostPath=cp;
			}		
			try{
				String wwsecurity_prev_uri=(String)request.getSession().getAttribute("wwsecurity_prev_uri");
				if(wwsecurity_prev_uri!=null&&!wwsecurity_prev_uri.isEmpty()){
					response.sendRedirect(hostPath+wwsecurity_prev_uri);
				}else{	
					response.sendRedirect(hostPath+successUrl);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}		
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}
	public int getStatus() {
		return status;
	}
	

}
