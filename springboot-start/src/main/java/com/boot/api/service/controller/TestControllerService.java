package com.boot.api.service.controller;

import com.boot.config.web.response.R;

public interface TestControllerService {

	R test2(String string, Integer i);

	String testNullEntity(String form);

	void tesReadWrite_W() throws Exception;

}
