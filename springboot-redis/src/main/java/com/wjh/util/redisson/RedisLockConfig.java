package com.wjh.util.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
        .setConnectionPoolSize(600)
        .setConnectionMinimumIdleSize(300)
        ;
        redisson = Redisson.create(config);
    }
 
    public RedissonClient getClient(){
        return redisson;
    }

}
