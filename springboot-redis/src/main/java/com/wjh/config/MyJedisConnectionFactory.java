package com.wjh.config;

import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

public class MyJedisConnectionFactory extends JedisConnectionFactory {


    public MyJedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        super(jedisPoolConfig);
    }

    public Jedis getResource(){
        return fetchJedisConnector();
    }

}
