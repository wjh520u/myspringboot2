package com.boot.config.web.configuration;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.boot.config.web.interceptor.LoginInterceptor;

/**
 * MVC配置
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Autowired
	private LoginInterceptor loginInterceptor;

	@Override
	public void addInterceptors( InterceptorRegistry registry ) {
		registry.addInterceptor( loginInterceptor ).addPathPatterns( "/**" );
	}

	@Override
	public void addArgumentResolvers( List< HandlerMethodArgumentResolver > argumentResolvers ) {
		// argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
		// argumentResolvers.add(pageHandlerResolve);
	}

	@Override
	public void addResourceHandlers( ResourceHandlerRegistry registry ) {
		registry.addResourceHandler( "/**" ).addResourceLocations( "classpath:/static/" );
		WebMvcConfigurer.super.addResourceHandlers( registry );
	}
	
	/**
     * @作者：王俊辉
     * 
     * @param converters 
     * 2019年8月14日下午3:36:41
     */
    @Override
	@Cacheable
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 1、需要先定义一个convert转换消息的对象；
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		//升级最新版本需加=============================================================
		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
		supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
		supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
		supportedMediaTypes.add(MediaType.APPLICATION_PDF);
		supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
		supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
		supportedMediaTypes.add(MediaType.APPLICATION_XML);
		supportedMediaTypes.add(MediaType.IMAGE_GIF);
		supportedMediaTypes.add(MediaType.IMAGE_JPEG);
		supportedMediaTypes.add(MediaType.IMAGE_PNG);
		supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
		supportedMediaTypes.add(MediaType.TEXT_HTML);
		supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
		supportedMediaTypes.add(MediaType.TEXT_PLAIN);
		supportedMediaTypes.add(MediaType.TEXT_XML);
		fastConverter.setSupportedMediaTypes(supportedMediaTypes);
        // 2、添加fastjson的配置信息，比如 是否要格式化返回json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.NotWriteDefaultValue);//不会返回值为0的原始属性
        // 3、在convert中添加配置信息.
        fastConverter.setFastJsonConfig(fastJsonConfig);
        // 4、将convert添加到converters当中.
        converters.add(0,fastConverter);
    }
}