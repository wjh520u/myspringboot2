package com.boot.config.properties.annotation.session;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Controller方法注解 ，忽略登录验证
 * @see com.ycxc.cjl.app.api.interceptor.AuthorizationInterceptor
 * @author 王俊辉
 * 2019年1月23日上午10:11:32
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreLogin {
	
}
