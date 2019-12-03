package com.boot.config.properties.exception;

import com.boot.config.properties.enums.ECodeAndMsg;

public class JsonException  extends BaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2918999174997428926L;

	public JsonException(String message) {
		super(message);
		this.code = ECodeAndMsg.PARAM_ERROR.CODE;
	}
	
	public JsonException(Integer code, String message) {
		super(code,message);
	}

	public JsonException(String message,Object data) {
		super(message);
		this.code = ECodeAndMsg.PARAM_ERROR.CODE;
		setData(data);
	}
	
	public JsonException(Integer code, String message,Object data) {
		super(code, message,data);
	}

}
