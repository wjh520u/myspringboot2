package com.boot.config.properties.annotation.lock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 王俊辉
 * 方法锁注解,使用spring spel语法
 * 参见：
 * @see com.ALockAop.cjl.app.api.resolver.LockAop
 * 
 * 2019年5月15日上午10:41:46
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Locker {
	
	/**
	 * @作者：王俊辉
	 */
	String value() default "";
	
	/**
	 * @作者：王俊辉
	 * 锁固定前缀，最终与value拼接成锁，
	 * 2019年5月15日上午10:40:03
	 */
	String pre() default "";
	
}
