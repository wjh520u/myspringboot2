package com.wjh.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
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
public class SentinelRedisConfig {

    private final Logger logger = LoggerFactory.getLogger(SentinelRedisConfig.class);

    /**
     * 创建连接工厂
     * @return
     */
    @Bean
    public JedisConnectionFactory createConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory jedisConnectionFactory = null;
        // 哨兵模式
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        try {
            // 加载JNDI配置(因为有些敏感信息运维是不希望IT直接看到的)
            Context context = new InitialContext();

            String [] serviceRedisNodes = "127.0.0.1:6379".split(",");
            // 转换成Redis点节
            Set<RedisNode> sentinelNodes = new HashSet<>();
            for(String node : serviceRedisNodes) {
                String[] ipAndPort = StringUtils.split(node, ":");
                String ip = ipAndPort[0];
                Integer port = Integer.parseInt(ipAndPort[1]);
                sentinelNodes.add(new RedisNode(ip, port));
            }
            redisSentinelConfiguration.setSentinels(sentinelNodes);
            redisSentinelConfiguration.setMaster("");
            // 创建连接工厂
            jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration, jedisPoolConfig);
        } catch (Exception e) {
            logger.error("创建Redis连接工厂错误：{}", e);
        }
        return jedisConnectionFactory;
    }

    /**
     * JedisPoolConfig 连接池
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.jedis.pool")
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        return jedisPoolConfig;
    }

}
