package com.boot.config.aop.lock;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.boot.config.properties.annotation.lock.ALock;
import com.boot.config.utils.AopUtil2;

/**
 * @see com.boot.config.properties.annotation.lock.ALock
 * 
 * @author 王俊辉 2019年5月22日下午2:40:00
 */
//@Aspect
//@Component
//@Order( 2 )
public class ALockAop {

	public static final ConcurrentHashMap< Object, AopUtil2.AopExetor > exe = new ConcurrentHashMap< Object, AopUtil2.AopExetor >();

	// 切点
	@Pointcut( "@annotation(com.boot.config.properties.annotation.lock.ALock)" )
	public void lockAspect() {
	}

	@Around( "lockAspect()" )
	public Object Lock( ProceedingJoinPoint joinPoint ) throws Throwable {

		Method methodSignature = ( (MethodSignature) joinPoint.getSignature() ).getMethod();

		ALock aLock = methodSignature.getAnnotation( ALock.class );
		String lockValue = aLock.value();
		String lockKey = "";
		// 有表达式
		if ( !lockValue.equals( "" ) ) {
			AopUtil2.AopExetor aopExetor = AopUtil2.getExetor( lockValue, "ALock", joinPoint, exe, true );
			long currentTimeMillis = System.currentTimeMillis();
			lockKey = aLock.prefix() + (String) aopExetor.executor( joinPoint );
			System.out.println( "ex用时" + ( System.currentTimeMillis() - currentTimeMillis ) );
		} else {
			lockKey = aLock.prefix() + lockValue;
		}

		// 如果锁为空，取类名和方法名做锁
		if ( lockKey.equals( "" ) ) {
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
		synchronized ( lockKey.intern() ) {
			Object proceed = joinPoint.proceed();
			return proceed;
		}
	}

}
