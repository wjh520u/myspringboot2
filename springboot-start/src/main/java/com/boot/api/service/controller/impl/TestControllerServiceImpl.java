package com.boot.api.service.controller.impl;

import javax.validation.constraints.NotNull;

import com.boot.api.dao.mapper.OrmUserMapper;
import com.boot.config.web.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.api.dao.entity.OrmUser;
import com.boot.api.service.controller.TestControllerService;
import com.boot.api.service.dao.IEntUserService;
import com.boot.config.properties.annotation.log.ALog;
import com.boot.config.properties.annotation.validtator.AValid;
import com.boot.config.properties.exception.DBException;
import org.springframework.transaction.annotation.Transactional;

@Service("testControllerService")
public class TestControllerServiceImpl implements TestControllerService {
	
	@Autowired
	com.boot.api.dao.mapper.OrmUserMapper ormUserMapper;

	@Override
	public R test2(String string, Integer code) {
		
		return R.success();
	}

	@Override
	@ALog(logExp = "{form}")
	@AValid
	public String testNullEntity(@NotNull(message = "form不能为空。") String form) {
		return "l";
	}
	
	@Override
	//@Transactional(transactionManager = "RW_transactionManager",rollbackFor = Throwable.class)
	public void tesReadWrite_W() throws Exception {
		long currentTimeMillis = System.currentTimeMillis();
		OrmUser byId = new OrmUser();//ormUserMapper.selectById("3");
		System.out.println(System.currentTimeMillis() - currentTimeMillis);
		System.out.println(byId.getPhoneNumber());
		byId.setPhoneNumber("130000");
		byId.setId(3);
		ormUserMapper.updateById(byId);
		throw new DBException("出错了。");
	}

}
