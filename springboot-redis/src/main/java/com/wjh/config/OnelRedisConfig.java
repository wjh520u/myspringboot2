package com.wjh.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.JedisClientConfigurationBuilderCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.HashSet;
import java.util.Set;

/**
 * Redis 配置集群
 */
//@Configuration
public class OnelRedisConfig {

    private final Logger logger = LoggerFactory.getLogger(OnelRedisConfig.class);

    /**
     * 创建连接工厂
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory createConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory jedisConnectionFactory = null;
        jedisConnectionFactory = new MyJedisConnectionFactory(jedisPoolConfig);
        jedisConnectionFactory.setUsePool(true);
        return jedisConnectionFactory;
    }

    /**
     * JedisPoolConfig 连接池
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.redis.jedis.pool")
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(200);
        return jedisPoolConfig;
    }

}
