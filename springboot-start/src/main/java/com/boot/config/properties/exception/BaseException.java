package com.boot.config.properties.exception;


public class BaseException extends RuntimeException  {

	public static final ThreadLocal<BaseException> exception = new ThreadLocal<BaseException>();
	
	public Integer code;
	
	public String msg;	
	
	public Object data;

	private static final long serialVersionUID = 1L;
	
	public BaseException(String message) {
		super(message);
		this.msg = message;
	}
	
	public BaseException(Integer code,String message) {
		super(message);
		this.code = code;
		this.msg = message;
	}
	
	public BaseException(Integer code,String message,Object data) {
		super(message);
		this.code = code;
		this.msg = message;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void throwGoalb(int code,String msg) {
		this.code = code;
		this.msg = msg;
		exception.set(this);
		throw new RuntimeException();
	}
	
	public void throwGoalb(String msg) {
		this.msg = msg;
		exception.set(this);
		throw new RuntimeException();
	}
	
	public void throwGoalb() {
		exception.set(this);
		throw new RuntimeException(getMessage());
	}
	
}
