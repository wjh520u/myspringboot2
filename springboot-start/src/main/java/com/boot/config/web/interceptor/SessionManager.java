package com.boot.config.web.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author 王俊辉
 * 
 * 用户会话管理器
 * 
 */
public class SessionManager {
	
	public String getUserId(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String userId = (String) request.getAttribute( "userId" );
		return userId;
	}

	public static Token getTokenInfo( String token ) {
		// TODO Auto-generated method stub
		return null;
	}

}
