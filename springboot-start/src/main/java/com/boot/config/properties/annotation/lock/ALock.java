package com.boot.config.properties.annotation.lock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 王俊辉
 * 方法锁注解
 * 
 * @value  表达式，如：'123456' ,  #methodParamName.id , 'edit'+ #methodParamName.id , 'all' + @beamName.filed
 * 参见：
 * @see com.ALockAop.cjl.app.api.resolver.ALockAop
 * 
 * 2019年5月15日上午10:41:46
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ALock {
	
	
	/**
	 * @作者：王俊辉
	 * 锁值，如：'123456' ,  #methodParamName.id , 'edit'+ #methodParamName.id , 'all' + @beamName.filed
	 * 2019年8月13日上午11:44:11
	 */
	String value() default "";
	
	/**
	 * @作者：王俊辉
	 * 锁固定前缀,最终与value拼接成锁
	 * 2019年5月15日上午10:40:03
	 */
	String prefix() default "";
	
	
	/**
	 * 是否跑出空引用异常
	 * @return 
	 * @Exception
	 * @author  王俊辉
	 * @date    2019年8月17日 下午1:08:57 
	 */
	boolean canThrowNullException() default true;
	
	
	/**
	 * 是否允许null值，空引用将视为null值
	 * @return 
	 * @Exception
	 * @author  王俊辉
	 * @date    2019年8月17日 下午1:12:30 
	 */
	boolean canNullValue() default true;
	
	
}
