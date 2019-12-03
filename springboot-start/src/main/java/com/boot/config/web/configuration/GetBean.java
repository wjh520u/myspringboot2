package com.boot.config.web.configuration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class GetBean  implements ApplicationContextAware  {

    public static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    	GetBean.ctx = applicationContext;
    }

    @SuppressWarnings("unchecked")
	public static <T> T get(String name) {
    	try{
    		T bean = (T) ctx.getBean(name);
    		return bean;
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }

    public static <T> T get(Class<T> requiredType) {
        try{
        	 return ctx.getBean(requiredType);
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }

    public static <T> T get(String name, Class<T> requiredType) {
        try{
        	return ctx.getBean(name, requiredType);
	   	}catch(Exception e){
	   		e.printStackTrace();
	   		return null;
	   	}
    }

}
