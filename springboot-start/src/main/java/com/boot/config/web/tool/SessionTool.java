package com.boot.config.web.tool;

public class SessionTool {
	
	public static final ThreadLocal<Object> entUserId = new ThreadLocal<Object>();
	
	public static Object getUserId() {
		return entUserId.get();
	}
	
}
