package com.boot.config.properties.annotation.session;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Controller方法注解 ，纳入登入校验
 * @see com.ycxc.cjl.app.api.interceptor.AuthorizationInterceptor
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
}
