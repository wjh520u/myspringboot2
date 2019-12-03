package com.boot.config.web.tool;



import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidator;

import com.boot.config.properties.exception.JsonException;

/**
 * hibernate-validator校验工具类
 *
 * 参考文档：http://docs.jboss.org/hibernate/validator/6.0/reference/en-US/html_single/
 *+
 */
public class ValidatorTool {

    private ValidatorTool(){}

    private static Validator validator;

    static {
        validator = Validation.byProvider( HibernateValidator.class )
                .configure()
                .failFast( true )
                .allowOverridingMethodAlterParameterConstraint(true)
                .buildValidatorFactory().getValidator();
    }
    
    /**
     * 校验对象
     * @param object        待校验对象
     * @throws Exception 
     * @throws RRException  校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object) throws Exception {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);

        if (!constraintViolations.isEmpty()) {
        	ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
            throw new JsonException(constraint.getMessage());
        }
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws Exception 
     * @throws RRException  校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object, Class<?>... groups) throws Exception {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);

        if (!constraintViolations.isEmpty()) {
        	ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
            throw new JsonException(constraint.getMessage());
        }
    }

	public static Validator getValidator() {
		return validator;
	}

	public static void setValidator(Validator validator) {
		ValidatorTool.validator = validator;
	}
    
}
