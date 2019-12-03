package com.boot.config.web.interceptor;

import java.util.Date;

public class Token {
	
	/**
	 * token
	 */
	private String token;
	
	/**
	 * 用户Id
	 */
	private String userId;
	
	/**
	 * 有效截止时间
	 */
	private Date validTime;

	public String getToken() {
		return token;
	}

	public void setToken( String token ) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId( String userId ) {
		this.userId = userId;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime( Date validTime ) {
		this.validTime = validTime;
	}

}
