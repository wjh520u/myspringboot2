package com.boot.config.web.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.StringUtils;
import org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean;

//@Configuration
public class BeanLisnerForSpringValidtor implements BeanPostProcessor  {
 
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    	//设置spring MVC 的默认校验，变为快速失败
        if (StringUtils.endsWithIgnoreCase(beanName, "mvcValidator")) {
    		
    		OptionalValidatorFactoryBean factoryBean = (OptionalValidatorFactoryBean) bean;
    		factoryBean.getValidationPropertyMap().put("hibernate.validator.fail_fast", "true");
        	
            return factoryBean;
        }
        return bean;
    }
 
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
