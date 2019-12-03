package com.boot;

import java.io.IOException;
import java.util.ArrayList;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;

@SpringBootApplication(scanBasePackages = "com.boot")
// @ImportResource(locations={"classpath:spring-ehcache.xml"})
@EnableCaching
@EnableWebMvc
@EnableAsync
@EnableTransactionManagement
public class AppStart extends SpringBootServletInitializer {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AppStart.class, args);
	}

	// 加载yml格式自定义配置文件
	//@Bean
	public static PropertySourcesPlaceholderConfigurer properties() throws IOException {
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
		yaml.setResources(new PathMatchingResourcePatternResolver().getResources("classpath:config/**/*.yml"));// File引入
		configurer.setProperties(yaml.getObject());
		return configurer;
	}

	// 打包启动本类
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AppStart.class);
	}

}
