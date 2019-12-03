package com.boot.config.properties.annotation.validtator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 王俊辉
 * 
 * 拦截校验方法，可在方法参数中使用校验注解
 * 
 * 2019年6月21日下午1:43:00
 */
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AValid {
}
