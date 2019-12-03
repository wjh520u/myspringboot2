package com.wjh.util;
import com.wjh.config.MyJedisConnectionFactory;
import com.wjh.util.redisson.RedisLocker;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;


/**
 *  spring boot 1.5.X
 * 使用redis 的 lua脚本  基于单点实现分布式锁
 *
 * lua脚本作为原子性操作，保证加锁和设置超时时间 为原子性操作
 * @author sxd
 * @date 2019/5/27 10:52
 */
public class RedisLock {

    private static final RedisTemplate<String,Object> redisTemplate = GetBean.get( "redisTemplate");

    private static final MyJedisConnectionFactory myJedisConnectionFactory = GetBean.get(MyJedisConnectionFactory.class);

    private static final Long SUCCESS = 1L;

    private Integer defaultLockSecondTime = 10;

    private static int maxLockLoop = 999999;

    public static String lock(String lockKey){
        return lock(lockKey, 30);
    }

    public static String lock(String lockKey,Integer lockSecondTime){
        String lockSecondTimes = lockSecondTime.toString();
        String uuid = UUID.randomUUID().toString();
        for (int i = 0; i < maxLockLoop  ; i++) {
            String lockVersion = lockProtected(lockKey,uuid ,lockSecondTimes);
            if (lockVersion != null && !lockVersion.equals("-1")){
                return lockVersion;
            }
        }
        throw new RuntimeException("获取锁失败.");
    }

    String script2 = "if redis.call('setNx',KEYS[1],ARGV[1])  then " +
            "   if redis.call('get',KEYS[1])==ARGV[1] then " +
            "      return redis.call('expire',KEYS[1],ARGV[2]) " +
            "   else " +
            "      return 0 " +
            "   end " +
            "end";

    static final String lockscript = "if not redis.call('get',KEYS[1]) then " +
            "   local incr = redis.call('incr','lock_version') " +
            "   if string.len(incr)>11 then " +
            "      redis.call('set','lock_version','0')" +
            "   end " +
            "   redis.call('set',KEYS[1],incr) " +
            "   redis.call('expire',KEYS[1],ARGV[1]) " +
            "   return incr " +
            "else " +
            "   return '-1';" +
            "end";

    static final String lockscript2 = "if redis.call('set',KEYS[1],ARGV[1],'EX',ARGV[2],'NX') then " +
            "   return ARGV[1];" +
            "else " +
            "   return '-1';" +
            "end";

    static final RedisScript<String> lockredisScript = new DefaultRedisScript<>(lockscript2, String.class);

    /**
     * 获取锁
     *
     * @param lockKey       redis的key
     * @param stringExpireTime    redis的key 的过期时间  防止死锁，导致其他请求无法正常执行业务
     * @return
     */
    protected static String  lockProtected(String lockKey, String version, String stringExpireTime) {

        Object result = redisTemplate.execute(lockredisScript, Collections.singletonList(lockKey), version , stringExpireTime);

        /*Jedis resource = myJedisConnectionFactory.getResource();
        String uuid = UUID.randomUUID().toString();
        String set = resource.set(lockKey, uuid, "NX", "EX", 10);
        if (set != null && !set.equals("0")){
            return uuid;
        }
        resource.close();*/
        if (result != null && !result.toString().equals("-1")){
            return version;
        }
        return result.toString();

    }

    static final String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]); else return 0 ;end";

    static final RedisScript<String> unlockRedisScript = new DefaultRedisScript<>(script, String.class);

    /**
     * 释放锁
     *
     * @param lockKey   redis的key
     * @param value     redis的value  只有value比对一致，才能确定是本请求 加的锁 才能正常释放
     * @return
     */
    public static boolean unlock(String lockKey, String value) {
        Object execute = redisTemplate.execute(unlockRedisScript, Collections.singletonList(lockKey), value);
        if (execute != null && execute.toString().equals("1")) {
            return true;
        }else {
            throw new RuntimeException("解锁失败.");
        }
    }

    public static int i = 0;
   public static class GuessANumber extends Thread {
        private int number;
        public GuessANumber() {
        }

        public void run() {
            //RLock lockKey = RedisLocker.lock("lockKey");

            String lockKey = lock("lockKey");

            System.out.println(i++);

            unlock("lockKey", lockKey);

            //lockKey.unlock();
        }
    }

}
