/*
 Filename:RequestInterceptor.java

 Author:Saurabh Kedia

 Description: Custom request interceptor for intercepting each and every response and request ,from and to the server 

 Date Modified:31.10.2018

 */
package com.college.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RequestInterceptor implements HandlerInterceptor {

	private String getPrincipal() {
		String username = null;
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			System.out.println(SecurityContextHolder.getContext()
					.getAuthentication().getAuthorities());

			username = ((UserDetails) principal).getUsername();

		} else {
			username = principal.toString();
		}

		return username;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception)
			throws Exception 
	{
		// TODO Auto-generated method stub

		System.out.println("interceptor after");
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

		System.out.println("interceptor post");
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {

		if ((request.getRequestURI().equals("/CollegeManagementSystem/")
				|| request.getRequestURI().equals("/CollegeManagementSystem/register") || request
				.getRequestURI().equals("/CollegeManagementSystem/login"))
				&& getPrincipal() != "anonymousUser") {
			System.out.println("reached interceptor");
			response.sendRedirect("/CollegeManagementSystem/updateDetails");
		}

		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			System.out.println(paramName);
			
			String[] paramValues = request.getParameterValues(paramName);
			for (int i = 0; i < paramValues.length; i++) {
				String paramValue = paramValues[i];
				
				if(paramValue.contains("<script"))
				{
					System.out.println("Injection detected");
					return false;
				}
				
				System.out.println(paramValue);
			}

		}
		
		
		System.out.println("interceptor pre");
		return true;

	}

}
