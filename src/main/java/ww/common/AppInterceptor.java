package ww.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AppInterceptor implements HandlerInterceptor {

	/** 
	* 执行时间:视图解析完毕 
	* 主要做一些监控的处理  比如 :异常  类是 try  catch 后面的 finally 
	*/
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	/** 
	* 执行时机:control 执行完, 视图解析没有把页面解析成页面 
	* 可以对视图做统一修改 主要提现在model上面(可以统一为 某个视图 添加头和尾 ) 
	*/ 
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**前置拦截 
	* 执行时机:在control之前执行 
	* true:拦截通过 表示可以访问control 
	* false:不可以访问control 
	* Object: 访问的control 的类的对象 
	* 可以做权限 校验和控制 
	*/ 
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		return true; //false 不可以访问control
	}

}
