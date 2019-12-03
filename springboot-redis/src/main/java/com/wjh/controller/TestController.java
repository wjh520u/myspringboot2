package com.wjh.controller;

import com.alibaba.fastjson.parser.ParserConfig;
import com.wjh.config.MyJedisConnectionFactory;
import com.wjh.entity.MyBean;
import com.wjh.util.RedisLock;
import com.wjh.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.Collections;

@RestController
public class TestController {

    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("test")
    public Object test(){

        String lockKey = RedisLock.lock("lockKey");

        System.out.println(1);

        RedisLock.unlock("lockKey", lockKey);

        //redisUtil.set("test222", "2222");
        //Object o = redisUtil.get("test222");

        //String script = "if redis.call('setnx',KEYS[1],ARGV[1]) == 1  then return 0; else return 1; end ";
        String script = "return redis.call('set',KEYS[1],ARGV[1],'EX',ARGV[2],'NX') ;";
        String script2 = "if redis.call('set',KEYS[1],ARGV[1],'EX',ARGV[2],'NX') then " +
                        "   return 1; " +
                        "else " +
                        "   return -1;" +
                        "end";

        RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);
        Object test = redisUtil.redisTemplate.execute(redisScript, Collections.singletonList("test222"), "test222", "10");
       /* MyJedisConnectionFactory connectionFactory = (MyJedisConnectionFactory) redisUtil.redisTemplate.getConnectionFactory();
        Jedis resource = connectionFactory.getResource();*/
        //Long test222 = resource.setnx("test222", "1233");
        return test;
    }


    @RequestMapping("testLock")
    public Object testLock(){
        for (int i = 0; i < 150 ; i++) {
            RedisLock.GuessANumber guessANumber = new RedisLock.GuessANumber();
            guessANumber.start();
        }
        return "test";
    }

}
