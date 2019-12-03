package com.annanion;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Transactional(transactionManager = "xatx" , rollbackFor = Throwable.class)
public @interface Transactional_Jta_Require {
}
