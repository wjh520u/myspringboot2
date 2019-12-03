package com.boot.config.properties.annotation.session;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义权限标识注解
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {
	
	/**
	 * @作者：王俊辉
	 * 自定义权限标识
	 * @return 
	 * 2019年8月8日下午4:01:10
	 */
	String value();
	
}
