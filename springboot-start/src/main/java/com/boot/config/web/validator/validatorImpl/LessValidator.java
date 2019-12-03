package com.boot.config.web.validator.validatorImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.boot.config.properties.annotation.validtator.Less;

public class LessValidator implements ConstraintValidator<Less, Object> {

	private Less less;
	
	@Override
	public void initialize(Less constraintAnnotation) {
		this.less = constraintAnnotation;
		
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value==null) {
			return true;
		}
		
		float parseDouble ;
		try {
			parseDouble = Float.parseFloat(value.toString());
			if (parseDouble>=less.value()) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
