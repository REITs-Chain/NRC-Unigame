package ww.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ww.authen.AdminValid;
import ww.controller.BaseController;
import ww.security.SecurityLogin;

@Controller
@RequestMapping(value="/login/AdminLogin")
public class AdminLogin extends BaseController {
	
	@Autowired
	private AdminValid valid;
	
	@RequestMapping(value="/login")
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView("/login/login");

		mv.addObject("message", "");
		return mv;
	}
	
	@RequestMapping(value="/login_in")
	public ModelAndView login_in(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView();
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String rememberMe=request.getParameter("rememberMe");
	    
	    boolean isRememberMe=false;
	    if(rememberMe!=null&&rememberMe.equals("on"))
	    	isRememberMe=true;
		
	    SecurityLogin sl=new SecurityLogin("adminfilter", request,response);
	    int res=sl.doLoginIn(username, password, isRememberMe);
		if(res==0){ //登录成功
			sl.RedirectSuccess();
			request.getSession().setAttribute("admin", sl.getLoginInfo().getUser());
			return null;
		}else{ //登录失败
			mv.addObject("message", "用户不存在或密码错误！");
			mv.addObject("username", username);
			mv.setViewName("login/login");
		}		
		
		return mv;
	}
	
	@RequestMapping(value="/login_out")
	public ModelAndView login_out(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView();
		
		SecurityLogin sl=new SecurityLogin("adminfilter", request,response);
	    sl.doLoginOut();
		
		request.getSession().removeAttribute("admin");
		request.getSession().removeAttribute("main_menus");
		mv.addObject("message", "");
		mv.setViewName("login/login");
		return mv;
	}
	
	@RequestMapping(value="/unauthorized")
	public ModelAndView unauthorized(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView();
//		request.getSession().removeAttribute("admin");
//		mv.addObject("message", "");
		mv.setViewName("login/unauthorized");
		return mv;
	}

}
