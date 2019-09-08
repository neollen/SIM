package com.ischoolbar.programmer.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.entity.User;

import net.sf.json.JSONObject;

/**
 * login filter
 * @author saxue
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String url=request.getRequestURI();
		System.out.println("interceptor,url= "+url);
		User user=(User)request.getSession().getAttribute("user");
		if(user==null) {//not login successfully
			
			System.out.println("login wrongly");
			if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
				Map<String, String> ret=new HashMap<>();
		    	 
		    	  ret.put("type", "error");
		   		  ret.put("msg", "please login again!");
		   		response.getWriter().write(JSONObject.fromObject(ret).toString());
		    	 
			}
			
			response.sendRedirect(request.getContextPath()+"/system/login");// redirect to the login page
			return false;
		}
		
		return true;
	}
	

}
