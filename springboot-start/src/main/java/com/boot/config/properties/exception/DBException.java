package com.boot.config.properties.exception;

import com.boot.config.properties.enums.ECodeAndMsg;

public class DBException extends BaseException {


	/**
	 * 
	 * @2019年4月9日上午11:59:18
	 */
	private static final long serialVersionUID = 1L;

	public DBException(Integer code, String message) {
		super(code, message);
	}
	
	public DBException(String message,Object data) {
		super(message);
		this.code = ECodeAndMsg.DB_EXCEPTION.CODE;
		setData(data);
	}
	public DBException(Integer code, String message,Object data) {
		super(code, message,data);
	}

	public DBException(String message) {
		super(message);
		this.code = ECodeAndMsg.DB_EXCEPTION.CODE;
	}

}
