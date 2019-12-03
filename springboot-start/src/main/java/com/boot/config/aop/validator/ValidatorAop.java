package com.boot.config.aop.validator;

import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.boot.config.properties.exception.JsonException;
import com.boot.config.web.tool.ValidatorTool;

/**
 * @author 王俊辉
 * 
 * 拦截@AValid注解方法
 * @see com.boot.config.properties.annotation.validtator.AValid
 * 方法参数校验，可在任意bean中使用
 * 
 * 2019年6月17日下午1:46:03
 */
@Component
@Aspect
@Order(1)
public class ValidatorAop {
	
	public static final ExecutableValidator validatorImpl = ((ExecutableValidator)ValidatorTool.getValidator().forExecutables());
	
	//切点    
    @Pointcut("@annotation(com.boot.config.properties.annotation.validtator.AValid)")    
    public  void aspect(){  
    } 
    
    @Before("aspect()")
    public void validator(JoinPoint joinPoint) throws Exception {
    	Method methodSignature = ((MethodSignature)joinPoint.getSignature()).getMethod();
    	
    	Set<ConstraintViolation<Object>> validateParameters = validatorImpl
    			.validateParameters(joinPoint.getThis() , methodSignature, joinPoint.getArgs());
    	
    	for (ConstraintViolation<Object> constraintViolation : validateParameters) {
			new JsonException(constraintViolation.getMessage()).throwGoalb();
		}
	}

}
