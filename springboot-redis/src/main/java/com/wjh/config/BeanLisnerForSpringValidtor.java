package com.wjh.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.PoolConfig;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.util.StringUtils;
import org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean;
import redis.clients.util.Pool;

@Configuration
public class BeanLisnerForSpringValidtor implements BeanPostProcessor  {
 
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    	//设置spring MVC 的默认校验，变为快速失败
        if (bean instanceof JedisClientConfiguration) {
    		return bean;
        }
        return bean;
    }
 
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
