package com.mapper;

import org.apache.ibatis.annotations.Insert;

public interface TestMapper {
    @Insert(value = "insert users (user_name) values (111)")
    public void insertTest() ;
}
