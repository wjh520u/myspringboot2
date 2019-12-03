package com.boot.config.aop.log;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jboss.logging.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.boot.config.properties.annotation.log.ALog;
import com.boot.config.utils.AopUtil;
import com.boot.config.utils.AopUtil.AopExetor;

/**
 * @author 王俊辉
 * @see com.boot.config.properties.annotation.log.ALog
 * 2019年5月22日下午2:41:31
 */
@Aspect    
@Component 
@Order(1)
public class ALogAop{
	
	/**
	 * 如果设置了该值，自定义替换表达式值，如日志表达式为：'我是[1]'，则取数组第一值替换'[1]'
	 * @2019年5月22日下午2:13:24
	 */
	public static ThreadLocal<Object[]> logObject = new ThreadLocal<Object[]>();
	
	public static final ConcurrentHashMap<Object, AopUtil.AopExetor> exe = new ConcurrentHashMap<Object, AopUtil.AopExetor>();
	
	//切点    
    @Pointcut("@annotation(com.boot.config.properties.annotation.log.ALog)")    
    public void logAspect(){  
    } 
    
    @Around("logAspect()")
    public Object Log(ProceedingJoinPoint joinPoint) throws Throwable {

    	
    	Exception ec = null;
    	Object proceed = null;
    	
    	try {
			proceed = joinPoint.proceed();
		} catch (Exception e) {
			ec = e;
			throw e;
		}finally {
			Object[] logObjects = logObject.get();
	    	//置空，防止线程池污染
			logObject.set(null);
	    	log(joinPoint,logObjects,proceed,ec);
		}
    	return proceed;
    }

	public void log(JoinPoint joinPoint, Object[] logObjects,Object returnObject,Exception e) throws Exception {
		Method methodSignature = ((MethodSignature)joinPoint.getSignature()).getMethod();
    	
    	ALog aLog = methodSignature.getAnnotation(ALog.class);
    	String logExp = aLog.logExp();
    	
    	//有表达式
    	if (logExp.indexOf("{") != -1) {
    		AopExetor aopExetor = exe.get(methodSignature);
        	if (aopExetor == null) {
        		synchronized ((methodSignature.getName()+"ALog").intern()) {
        			if (exe.get(methodSignature) == null) {
        				AopUtil.createAopExecutetor(logExp,"ALog",joinPoint,exe,returnObject,true);
    				}
    			}
        		aopExetor = exe.get(methodSignature);
    		}
        	logExp = (String)aopExetor.executor(joinPoint,returnObject);
		}
    	if (logObjects != null) {
			for (int i = 0; i < logObjects.length; i++) {
				logExp = logExp.replace("["+(i+1)+"]", logObjects[i].toString());
			}
		}
    	System.err.println("日志log："+logExp);
    	Logger logger = Logger.getLogger(joinPoint.getTarget().getClass());
    	logger.error(logExp);
	}
	
	/**
	 * @作者：王俊辉
	 * 自定义日志数组值，替换如：[1]，[2]等。
	 * @param object 
	 * 2019年5月22日下午2:48:28
	 */
	public static void setLogObjects(Object... object) {
		if (object != null) {
			logObject.set(object);
		}
	}
	
}

