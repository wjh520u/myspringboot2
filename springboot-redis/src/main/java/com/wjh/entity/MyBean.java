package com.wjh.entity;

import java.io.Serializable;

public class MyBean implements Serializable {
    public MyBean(String id) {
        this.id = id;
    }

    public String id;

}
