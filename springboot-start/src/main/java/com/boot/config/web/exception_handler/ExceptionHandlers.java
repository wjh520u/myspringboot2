package com.boot.config.web.exception_handler;


import javax.servlet.http.HttpServletRequest;

import com.boot.config.web.response.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.boot.config.properties.enums.ECodeAndMsg;
import com.boot.config.properties.exception.BaseException;

/**
 * 异常处理器
 *
 */
@RestControllerAdvice
public class ExceptionHandlers {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(BaseException.class)
	public R handleBaseException(BaseException e){
		e.printStackTrace();
		R r = new R();
		r.put("code", e.getCode());
		r.put("msg", e.getMsg());
		if (e.getData() != null) {
			r.put("data", e.getData());
		}
		return r;
	}

	/**
	 * 处理自定义异常
	 * @throws Exception 
	 */
	@ExceptionHandler(RuntimeException.class)
	public R handleRuntimeException(RuntimeException e) throws Exception{
		BaseException baseException = BaseException.exception.get();
		R r = new R();
		if (baseException != null) {
			//置空，防止线程池污染
			BaseException.exception.set(null);
			//交给基础异常处理
			return handleBaseException(baseException);
		} else {
			e.printStackTrace();
			r.put("code", 500);
			r.put("msg", e.getMessage());
		}
		return r;
	}

	/**
	 * @作者：王俊辉
	 * 
	 * 处理其余异常
	 * 
	 * 2019年5月21日下午4:51:12
	 */
	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		logger.error(e.getMessage(), e);
		
		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		log(request,e);
		return R.error(ECodeAndMsg.PARAM_ERROR.CODE,e.getMessage());
	}
	
	/**
	 * @作者：王俊辉
	 * 
	 * spring 方法参数@Valid注解校验异常处理
	 * 
	 * 2019年5月21日下午4:50:02
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public R MethodArgumentNotValidException(MethodArgumentNotValidException e){
		
		BindingResult bindingResult = e.getBindingResult();
		
		if ( bindingResult != null && bindingResult.getErrorCount() > 0 ) {
			ObjectError objectError = bindingResult.getAllErrors().get(0);
			return R.error(ECodeAndMsg.PARAM_ERROR.CODE,objectError.getDefaultMessage());
		}
		return R.error(ECodeAndMsg.PARAM_ERROR.CODE,e.getMessage());
	}

	/**
	 * 异步记录异常日志
	 * 
	 * @作者：王俊辉
	 * 
	 * 2019年3月13日上午10:10:22
	 */
	private void log(HttpServletRequest request, Exception e) {
		
	}
	
}
