package com.boot.config.web.validator.validatorImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.boot.config.properties.annotation.validtator.Greater;

public class GreaterValidator  implements ConstraintValidator<Greater, Object> {
	
	private Greater greater;
	
	@Override
	public void initialize(Greater constraintAnnotation) {
		this.greater = constraintAnnotation;
		
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value==null) {
			return true;
		}
		float parseDouble ;
		try {
			parseDouble = Float.parseFloat(value.toString());
			if (parseDouble<=greater.value()) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
