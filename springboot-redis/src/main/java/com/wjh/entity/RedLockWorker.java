package com.wjh.entity;

public interface RedLockWorker<T> {
	
	T invokeAfterLockAquire() throws Exception;
	
}
