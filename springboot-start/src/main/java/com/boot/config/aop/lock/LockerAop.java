package com.boot.config.aop.lock;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.annotation.Order;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.boot.config.properties.annotation.lock.Locker;
import com.boot.config.web.configuration.GetBean;

/**
 * @see com.boot.config.properties.annotation.lock.ALock
 * 
 * @author 王俊辉 2019年5月22日下午2:40:00
 */
//@Aspect
//@Component
//@Order( 2 )
public class LockerAop {

	public static final ConcurrentHashMap< Object, Expression > exe = new ConcurrentHashMap< Object, Expression >();

	private static BeanFactoryResolver beanFactoryResolver = null;

	// 切点
	@Pointcut( "@annotation(com.boot.config.properties.annotation.lock.Locker)" )
	public void lockAspect() {
	}

	@Around( "lockAspect()" )
	public Object Lock( ProceedingJoinPoint joinPoint ) throws Throwable {

		Method methodSignature = ( (MethodSignature) joinPoint.getSignature() ).getMethod();

		Locker lock = methodSignature.getAnnotation( Locker.class );
		String lockValue = lock.value();

		if ( !lockValue.trim().equals( "" ) ) {
			Expression expression = exe.get( lockValue );
			if ( expression == null ) {
				synchronized ( lockValue.intern() ) {
					if ( exe.get( lockValue ) == null ) {
						expression = new SpelExpressionParser( new SpelParserConfiguration( SpelCompilerMode.IMMEDIATE,
								getClass().getClassLoader(), true, true, 5 ) ).parseExpression( lockValue );
						// 缓存解析器
						exe.put( lockValue, expression );
					}
					expression = exe.get( lockValue );
				}
			}
			StandardEvaluationContext context = new StandardEvaluationContext();
			if ( beanFactoryResolver == null ) {
				beanFactoryResolver = new BeanFactoryResolver( GetBean.ctx );
			}
			context.setBeanResolver( beanFactoryResolver );
			context.setRootObject( joinPoint.getThis() );

			long currentTimeMillis = System.currentTimeMillis();
			Object [] args = joinPoint.getArgs();
			String [] parameterNames = ( (MethodSignature) joinPoint.getSignature() ).getParameterNames();
			for ( int i = 0; i < parameterNames.length; i++ ) {
				context.setVariable( parameterNames[i], args[i] );
			}
			Object value = expression.getValue( context );

			lockValue = lock.pre() + value;

			System.out.println( "锁解析用时：" + ( System.currentTimeMillis() - currentTimeMillis ) );
		}

		// 如果锁为空，取类名和方法名做锁
		if ( lockValue.equals( "" ) ) {
			lockValue = joinPoint.getTarget().getClass().getName() + "." + methodSignature.getName();
		}
		System.out.println( "锁值:" + lockValue );

		// 分布式加锁
		/*
		 * RLock lock = RedisLocker.lock(lockKey); Object proceed = null; try {
		 * proceed = joinPoint.proceed(); } catch (Exception e) { throw e;
		 * }finally { RedisLocker.unLock(lock); } return proceed;
		 */

		// 单机锁
		synchronized ( lockValue.intern() ) {
			Object proceed = joinPoint.proceed();
			return proceed;
		}
	}

}
