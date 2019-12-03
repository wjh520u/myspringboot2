package com.boot.config.properties.annotation.validtator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.boot.config.web.validator.validatorImpl.GreaterValidator;

@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GreaterValidator.class)
@Documented
public @interface Greater {
	
	float value();

	String message() default "数值超出下限。";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
