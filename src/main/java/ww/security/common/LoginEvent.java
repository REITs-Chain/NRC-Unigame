package ww.security.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ww.security.SecurityManager;

public abstract class LoginEvent {

	/**
	 * 登录成功后执行
	 * @param li
	 * @param filterName
	 * @param request
	 * @param response
	 * @param sManager
	 */
	public abstract void onAfterSuccess(LoginInfo li,String filterName,
			HttpServletRequest request,
			HttpServletResponse response,SecurityManager securityManager);
	
	/**
	 * 登录失败后执行
	 * @param li
	 * @param filterName
	 * @param request
	 * @param response
	 * @param sManager
	 */
	public abstract void onAfterFaied(LoginInfo li,String filterName,
			HttpServletRequest request,
			HttpServletResponse response,SecurityManager sManager);

}
