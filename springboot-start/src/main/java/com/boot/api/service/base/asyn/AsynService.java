package com.boot.api.service.base.asyn;

import javax.validation.Valid;

import com.boot.api.properties.bean.controller.Test2Form;

public interface AsynService {
	
	Test2Form addI(Test2Form test2Form);

    
    /**
     * @param name
     * @return 
     * @Exception
     * @author  王俊辉
     * @date    2019年8月15日 下午5:27:58 
     */
    Test2Form test2(@Valid Test2Form name);

}
