package com.boot.api.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.boot.config.web.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.api.dao.mapper.OrmUserMapper;
import com.boot.api.properties.bean.controller.Test2Form;
import com.boot.api.service.base.asyn.AsynService;
import com.boot.api.service.controller.TestControllerService;
import com.boot.config.aop.log.ALogAop;
import com.boot.config.cache.CacheHelper;
import com.boot.config.properties.annotation.lock.ALock;
import com.boot.config.properties.annotation.log.ALog;
import com.boot.config.properties.annotation.session.IgnoreLogin;
import com.boot.config.properties.annotation.session.IgnorePermission;
import com.boot.config.properties.exception.JsonException;
import com.project.utils.RegexTool;

@RestController
public class TestController {

	@Autowired
	public TestControllerService testControllerService;

	@Autowired
	AsynService asynService;

	@Autowired
	OrmUserMapper ormUserMapper;

	@RequestMapping( "test" )
	@ALock( value = "" )
	@ALog( logExp = "{id}" )
	@Valid
	// @Cacheable(key = "#id",cacheNames = CCacheName.DEFAULT_CACHE)
	public R test(@NotBlank String id ) {
		System.out.println( R.success( id ) );
		return R.success( id );
	}

	@RequestMapping( "test2" )
	@ALock( value = "hnihihn" )
	@ALog( logExp = "id是{ $testControllerService.test2(<form:id>,45) + form.name }，我叫{#return},还有那个手动设置值：[1]" )
	public R test2( Test2Form form ) throws JsonException {
		ALogAop.setLogObjects( "王俊辉" );
		Test2Form test2Form = new Test2Form();
		test2Form.setName( "54" );
		testControllerService.testNullEntity( null );
		return testControllerService.test2( "123456", 5 );
	}

	@RequestMapping( "testLock" )
	public R generatorCJL( String id ) {
		for ( int i = 0; i < 1; i++ ) {
			Test2Form test2Form = new Test2Form();
			test2Form.id= "123" ;
			asynService.addI( null );
			asynService.test2(test2Form);
		}
		return R.success( id );
	}

	@RequestMapping( "testReadWrite" )
	public R testReadWrite( String id ) throws Exception {
		testControllerService.tesReadWrite_W();
		return R.success( id );
	}

	@RequestMapping( "testLogin" )
	@IgnoreLogin
	@IgnorePermission
	public R testLogin( @NotBlank( message = "id不能为空。" ) String id ) throws Exception {
		System.out.println( "执行方法" );
		Test2Form javaObject = CacheHelper.get( "defaultCache", "123", Test2Form.class );
		return R.success( id );
	}
	
	@RequestMapping( "testCache" )
    @IgnoreLogin
    public R testCache( ) throws Exception {
        System.out.println( "执行方法" );
        Test2Form javaObject = CacheHelper.get( "defaultCache", "123", Test2Form.class );
        return R.success( );
    }

	public static void main( String [] args ) {
		String test = "!!!$oosmsdnlondndknnjo55 ~~---$yyy7=-`";
		System.out.println( RegexTool.matchAll( "(\\$[a-zA-z0-9]{1,})", test ) );
	}

	@RequestMapping( "testex" )
	@ALock(value = "wo{#id}")
	public Object testEx(String id){
		return R.success();
	}

}
