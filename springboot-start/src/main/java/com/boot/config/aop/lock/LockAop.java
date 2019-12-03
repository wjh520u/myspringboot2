package com.boot.config.aop.lock;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import com.boot.config.utils.AopUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.boot.config.properties.annotation.lock.ALock;
import com.boot.config.utils.AopUtil2;
import com.boot.config.web.configuration.GetBean;

/**
 * @see com.boot.config.properties.annotation.lock.ALock
 * 
 * @author 王俊辉 2019年5月22日下午2:40:00
 */
@Aspect
@Component
@Order( 2 )
public class LockAop {

	public static final ConcurrentHashMap< Object, AopUtil2.AopExetor > exe = new ConcurrentHashMap< Object, AopUtil2.AopExetor >();

	// 切点
	@Pointcut( "@annotation(com.boot.config.properties.annotation.lock.ALock)" )
	public void lockAspect() {
	}

	@Around( "lockAspect()" )
	public Object Lock( ProceedingJoinPoint joinPoint ) throws Throwable {
	    Object[] args = joinPoint.getArgs();
        if (args.length==0) {
            throw new Exception("没有参数，不支持锁。");
        }
        Method methodSignature = ((MethodSignature)joinPoint.getSignature()).getMethod();
        ALock aLock = methodSignature.getAnnotation(ALock.class);
        String lockValue = aLock.value();
        
        String lockKey = "";
        if (!lockValue.equals("")) {
            long currentTimeMillis = System.currentTimeMillis();
            String expressionValue = AopUtils.getExpressionValue(lockValue,joinPoint,aLock.canThrowNullException(),aLock.canNullValue());
            //汇总锁字符串
            lockKey += expressionValue;
            System.out.println("用时："+(System.currentTimeMillis() - currentTimeMillis)+lockKey);
        }
        lockKey = aLock.prefix() + lockKey;
		// 如果锁为空，取类名和方法名做锁
		if ( lockKey.toString().equals( "" ) ) {
			// System.out.println("锁对象为空。锁方法。");
			lockKey = joinPoint.getTarget().getClass().getName() + "." + methodSignature.getName();
		}
		System.out.println( "锁lock:" + lockKey );
		// 分布式加锁
		/*
		 * RLock lock = RedisLocker.lock(lockKey); Object proceed = null; try {
		 * proceed = joinPoint.proceed(); } catch (Exception e) { throw e;
		 * }finally { RedisLocker.unLock(lock); } return proceed;
		 */

		// 单机锁
		synchronized ( lockKey.toString().intern() ) {
			Object proceed = joinPoint.proceed();
			return proceed;
		}
	}
}
