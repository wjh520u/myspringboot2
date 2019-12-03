package com.wjh.util.redisson;

import com.wjh.entity.RedLockWorker;
import com.wjh.util.GetBean;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class RedisLocker {

    /**
     * 锁前缀
     */
    private final static String LOCKER_PREFIX = "lock:";

    private static final RedisLockConfig redisLockConfig = GetBean.get(RedisLockConfig.class);
    
    public <T> T lock(String lockName, RedLockWorker<T> worker) throws Exception {
        return lock(lockName, worker, 3000);
    }
    
    public <T> T lock(String lockName, RedLockWorker<T> worker, int lockTime) {
        RedissonClient redisson= redisLockConfig.getClient();
        RLock lock = redisson.getLock(LOCKER_PREFIX + lockName);
        
        boolean success = false;
		try {
			success = lock.tryLock(1000, lockTime, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        if (success) {
            try {
                return worker.invokeAfterLockAquire();
            } catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} finally {
                lock.unlock();
            }
        }
		throw new RuntimeException("服务器繁忙！");
    }
    
    public static RLock lock(String lockName,long lockTime) {
    	RedissonClient redisson= redisLockConfig.getClient();
        RLock lock = redisson.getLock(LOCKER_PREFIX + lockName);
        boolean success = false;
		try {
			success = lock.tryLock(5000, lockTime, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        if (!success) {
			throw new RuntimeException("服务器事物繁忙。");
		}
        return lock;
	}
    
    public static RLock lock(String lockName)  {
    	RedissonClient redisson= redisLockConfig.getClient();
        RLock lock = redisson.getLock(LOCKER_PREFIX + lockName);
        boolean success = false;
		try {
			success = lock.tryLock(5000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        if (!success) {
			throw new RuntimeException("服务器事物繁忙。");
		}
        return lock;
	}

}