package com.mapper2;

import org.apache.ibatis.annotations.Insert;

public interface Test2Mapper {

    @Insert(value = "insert users (user_name) values (111)")
    public void insertTest() ;
}
