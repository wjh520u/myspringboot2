package com.service.impl;

import com.annanion.Transactional_Jta_Require;
import com.mapper.TestMapper;
import com.mapper2.Test2Mapper;
import com.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestMapper testMapper;

    @Autowired
    Test2Mapper test2Mapper;

    @Override
    @Transactional_Jta_Require
    public Map<String, Object> test() {
        testMapper.insertTest();
        test2Mapper.insertTest();
        int a = 1/0;
        return null;
    }
}
