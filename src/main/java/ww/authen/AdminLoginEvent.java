package ww.authen;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import model.Admin;
import ww.security.SecurityManager;
import ww.security.common.LoginEvent;
import ww.security.common.LoginInfo;

public class AdminLoginEvent extends LoginEvent {

	@Autowired
	private AdminValid adminValid;
	
	public AdminLoginEvent() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onAfterSuccess(LoginInfo li, String filterName, HttpServletRequest request,
			HttpServletResponse response, SecurityManager securityManager) {
		
		Admin admin=adminValid.getAdmin(li.userName);
		
		request.getSession().setAttribute("admin", admin);
		
	}

	@Override
	public void onAfterFaied(LoginInfo li, String filterName, HttpServletRequest request, HttpServletResponse response,
			SecurityManager sManager) {
		
	}

}
