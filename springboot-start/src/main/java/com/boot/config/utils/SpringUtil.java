package com.boot.config.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SpringUtil {
	
	public HttpServletRequest getRequest(){
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public HttpServletResponse getResponse(){
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

}
