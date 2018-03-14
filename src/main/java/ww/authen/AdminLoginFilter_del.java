package ww.authen;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ww.common.ModelDAO;
import model.Admin;

public class AdminLoginFilter_del implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {	
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		HttpSession session=req.getSession();
		
		String URI=req.getRequestURI();
		session.setAttribute("ww_raw_url", URI);
		
		
		if(URI.indexOf("/login")>=0||URI.indexOf("/dologin")>=0){
			chain.doFilter(request, response);
			return;
		}
		
		if(session.getAttribute("admin")==null){//未登陆
			session.setAttribute("gourl_admin", req.getServletPath());
			RequestDispatcher dispatcher=request.getRequestDispatcher("/admin/login");
			dispatcher.forward(request, response);
			//res.sendRedirect("admin/login");
		}else{//已登陆
			/*
			//验证权限 begin 
			String sp=req.getServletPath();
			//String app=req.getContextPath();
			String[] strs=sp.split("/"); 
			if(strs.length>=4){
				String moudel=strs[1];
				String controller=strs[2]; //framename
				String action=strs[3];
				if(RightValid.hasFrame(dao, controller)){
					if(action.equalsIgnoreCase("list")){								
						if(!RightValid.Valid(controller, "list",req,res)){
							return;
						}
					}else if(action.equalsIgnoreCase("delete")){								
						if(!RightValid.Valid(controller, "delete",req,res)){
							return;
						}
					}else if(action.equalsIgnoreCase("edit")){
						int state=WwSystem.ToInt(req.getParameter("state"));
						if(state==0){ //新增
							if(!RightValid.Valid(controller, "new",req,res)){
								return;
							}
						}else if(state==1){ //修改
							if(!RightValid.Valid(controller, "edit",req,res)){
								return;
							}
						}else if(state==2){ //查看
							if(!RightValid.Valid(controller, "view",req,res)){
								return;
							}
						}
					}else{
						if(RightValid.hasRight(dao, action)){
							if(!RightValid.Valid(controller, action,req,res)){
								return;
							}
						}else{
							//如果找不到该权限项，将不做权限控制。
						}
					}
				}
			}
			//验证权限 end
			*/
			chain.doFilter(request, response);
		}
				

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
