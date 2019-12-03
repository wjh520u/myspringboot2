package com.boot.config.cache;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

import net.sf.ehcache.CacheManager;
@Configuration
public class EhcacheCacheConfig {
	
	@Bean("cacheManager")
    public CacheManager EhCacheManagerFactoryBean() {
    	EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
    	ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
    	ehCacheManagerFactoryBean.setShared(true);
    	//必须初始化
    	ehCacheManagerFactoryBean.afterPropertiesSet();
    	return ehCacheManagerFactoryBean.getObject();
	}
    
    @Bean("ehCacheCacheManager")
    @Primary
    public EhCacheCacheManager ehCacheCacheManager(@Qualifier("cacheManager")CacheManager cacheManager) {
    	EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager(cacheManager);
    	//必须初始化
    	ehCacheCacheManager.afterPropertiesSet();
    	System.out.println(ehCacheCacheManager.getCacheNames());
        return ehCacheCacheManager;
    }
  
}