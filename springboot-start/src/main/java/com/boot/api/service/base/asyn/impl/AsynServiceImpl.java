package com.boot.api.service.base.asyn.impl;

import javax.validation.Valid;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.boot.api.properties.bean.controller.Test2Form;
import com.boot.api.service.base.asyn.AsynService;
import com.boot.config.aop.log.ALogAop;
import com.boot.config.properties.annotation.lock.ALock;
import com.boot.config.properties.annotation.log.ALog;

@Service
public class AsynServiceImpl implements AsynService {

	private int i = 0;

	@Async
	@Override
	@ALock( value = " 'edit' + #name.id +  #name.name", canThrowNullException = false , canNullValue = true)
	@ALog( logExp = "894884   [1]" )
	//@Cacheable(cacheNames = "defaultCache" , key = "#p0.id")
	public Test2Form addI( @Valid Test2Form name ) {
		System.out.println(  );
		
		ALogAop.setLogObjects( "wjh" + i );
		int a = (Integer) 2;
		System.out.println( ++i );
		return name;
	}
	
	@Override
	@ALock( value = " null + #name.name ")
    public Test2Form test2( @Valid Test2Form name ) {
        
        return name;
    }

	public int getI() {
		return i;
	}

	public void setI( int i ) {
		this.i = i;
	}

}
