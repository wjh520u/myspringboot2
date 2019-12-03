package com.boot.config.aop.db;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.boot.config.db.datasource.read_write_split.ReadAndWriteDataSourceConfig;

@Component
@Aspect
@Order(1)
public class ReadWriteSplitAop {
	
	@Pointcut("@annotation(com.boot.config.properties.annotation.db.ReadDB)")
	public void readDBAspect() {}
	
	@Pointcut("execution (* com.boot.api.service..*.*_R(..))")
	public void methodSufAspect_R() {}
	
	@Before("readDBAspect() or methodSufAspect_R()")
	public void readAspectAop(JoinPoint joinPoint) {
    	String dbSelect = ReadAndWriteDataSourceConfig.dbSelect.get();
		if (dbSelect == null) {
			ReadAndWriteDataSourceConfig.dbSelect.set("R");
		}
	}
	
	@Pointcut("@annotation(com.boot.config.properties.annotation.db.WriteDB) ")
	public void writeDBAspect() {
		
	}
	
	@Pointcut("execution (* com.boot.api.service..*.*_W(..))")
	public void methodSufAspect_W() {
			
	}
	
	@Around("writeDBAspect() or methodSufAspect_W()")
	public Object writeAspectAop(ProceedingJoinPoint joinPoint) throws Throwable {
    	String dbSelect = ReadAndWriteDataSourceConfig.dbSelect.get();
		if (dbSelect == null || dbSelect.equals("R")) {
			ReadAndWriteDataSourceConfig.dbSelect.set("W");
		}
		Object proceed = null;
		try {
			proceed = joinPoint.proceed();
		} catch (Exception e) {
			throw e;
		}
		//写库执行完后需要重新区分读写库与防止线程池污染
		ReadAndWriteDataSourceConfig.dbSelect.set(null);
		return proceed;
	}

}
