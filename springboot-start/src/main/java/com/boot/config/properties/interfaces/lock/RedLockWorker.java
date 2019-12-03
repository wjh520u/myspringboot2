package com.boot.config.properties.interfaces.lock;

public interface RedLockWorker<T> {
	
	T invokeAfterLockAquire() throws Exception;
	
}
