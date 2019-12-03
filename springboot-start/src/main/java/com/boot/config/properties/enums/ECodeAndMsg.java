package com.boot.config.properties.enums;

public enum ECodeAndMsg {
	
	/**
	 * 参数异常。
	 */
	PARAM_ERROR(201,"参数异常。"), 
	/**
	 * 数据处理异常
	 */
	DB_EXCEPTION(204,"数据处理异常."),
	
	/**
	 * 锁异常
	 */
	LOCK_EXCEPTION(207,"数据处理异常."),
	
	TOKEN_WITHOUT(403,"请重新登录。"),
	
	TOKEN_TIMEOUT(403,"请重新登录。");
	
	public final int CODE;
	
	public final String MSG;
	
	private ECodeAndMsg(int code,String msg) {
		this.CODE = code;
		this.MSG = msg;
	}

}
