package com.boot.config.lock;

import javax.annotation.PostConstruct;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

//@Component
public class RedisLockConfig {
	
	RedissonClient redisson;
	
    @PostConstruct
    public void init(){
    	Config config = new Config();
        config.useSingleServer()
        .setTimeout(1000000)
        .setAddress("redis://127.0.0.1:6379")
        .setDatabase(0)
        .setConnectionPoolSize(300)
        .setConnectionMinimumIdleSize(30)
        ;
        redisson = Redisson.create(config);
    }
 
    public RedissonClient getClient(){
        return redisson;
    }

}
