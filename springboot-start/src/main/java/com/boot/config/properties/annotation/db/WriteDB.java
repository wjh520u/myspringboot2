package com.boot.config.properties.annotation.db;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 王俊辉
 * 
 * 写库方法执行注解
 * 
 * 2019年6月6日下午3:01:51
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WriteDB {

}
