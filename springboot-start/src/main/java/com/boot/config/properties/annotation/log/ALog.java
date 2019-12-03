package com.boot.config.properties.annotation.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 王俊辉
 * 方法日志注解
 * 参见：
 * @see  com.boot.config.aop.log.ALogAop
 * 
 * 2019年5月15日上午10:41:46
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ALog {
	
	
	/**
	 * @作者：王俊辉
	 * 
	 * 日志字符串，可带参数表达式，如：'打印日志，第一个方法参数是{ex}'
	 * ex表达式，支持加号   如：
		 * {user} , {user.id+user.name} , {user.id} , 
		 * {$logService.lock()} , { $logService.lock(<user>) }，{$logService.lock(<user:id>)} , {$logService.lock('abc',123,1f,2l,3d,4b)}
		 * {#this} , {#return}
		 * 解释：
		 * {user}表示方法参数
		 * $logService表示bean名称为logService的bean 可调用方法，如 <user>为该AOP方法参数 访问属性时需用冒号如<user:id>，其他参数如'abc'为字符串："123"， 123为Integer:123 ， 1f为Float：1 ， 2l为Long:2，以此类推
		 * {#this} 相当于AOP所切bean的this
		 * {#return} 相当于AOP所切方法的返回值,需AOP实现
		 * 
	 *也可以自定义数值：
	 *需要 @see com.boot.config.aop.log.ALogAop 的setLogObjects方法设置对应值
	 *如 ：'记录自定义值：[1]和[2]'，在执行方法时，需设置值，调用ALogAop.setLogObjects("数值1","数值2")
	 *
	 * 
	 * 2019年5月22日下午2:42:21
	 */
	String logExp() default "";
	
}
