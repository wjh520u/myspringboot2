package com.boot.config.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import com.boot.config.properties.exception.LockException;
import com.boot.config.properties.interfaces.lock.RedLockWorker;
import com.boot.config.web.configuration.GetBean;

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
				new LockException(e.getMessage()).throwGoalb();
			} finally {
                lock.unlock();
            }
        }
        new LockException("服务器繁忙！").throwGoalb();
        return null;
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
			new LockException("服务器事物繁忙。").throwGoalb();
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
			new LockException("服务器事物繁忙。").throwGoalb();
		}
        return lock;
	}
    
    public static boolean unLock(RLock rLock) {
		if (rLock != null) {
			if (rLock.isHeldByCurrentThread()) {
				rLock.unlock();
			}
			return true;
		}
		return false;
	}

}