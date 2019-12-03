package com.boot.config.cache;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;

//@Configuration
// @EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {
	// 自定义key生成方式

	// 缓存管理器
	@Bean( value = "redisCacheManager" )
	@Primary
	public CacheManager cacheManager( RedisConnectionFactory jedisConnectionFactory ) {

		RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();

		FastJsonRedisSerializer< Object > fastJsonRedisSerializer = new FastJsonRedisSerializer< Object >(
				Object.class );

		SerializationPair< String > keySerializationPair = RedisSerializationContext
				.fromSerializer( new StringRedisSerializer() ).getKeySerializationPair();
		SerializationPair< ? > valueSerializationPair = RedisSerializationContext
				.fromSerializer( fastJsonRedisSerializer ).getValueSerializationPair();
		// 默认配置
		defaultCacheConfig.serializeKeysWith( keySerializationPair );
		defaultCacheConfig.serializeValuesWith( valueSerializationPair );
		defaultCacheConfig.entryTtl( Duration.ofSeconds( 1 ) );

		// 自定义缓存名称配置
		Map< String, RedisCacheConfiguration > cacheNameRedisConfig = new HashMap< String, RedisCacheConfiguration >();
		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
				.serializeKeysWith( keySerializationPair ).serializeValuesWith( valueSerializationPair )
				.entryTtl( Duration.ofSeconds( 10 ) );
		cacheNameRedisConfig.put( "testCacheName4", cacheConfig );
		cacheNameRedisConfig.put( "defaultCache", cacheConfig );

		RedisCacheManager cacheManager = new RedisCacheManager(
				RedisCacheWriter.nonLockingRedisCacheWriter( jedisConnectionFactory ), defaultCacheConfig,
				cacheNameRedisConfig );
		cacheManager.afterPropertiesSet();
		System.out.println( cacheManager.getCacheNames() );
		return cacheManager;
	}

}