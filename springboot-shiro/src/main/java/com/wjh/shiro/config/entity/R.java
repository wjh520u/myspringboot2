package com.wjh.shiro.config.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class R {

    @JSONField(ordinal = 1)
    private int code;

    @JSONField(ordinal = 2)
    private String message;

    @JSONField(ordinal =3)
    private Object data;

    public R() {
        this(0, "success", null);
    }

    public R(String message) {
        this(0, message, null);
    }

    public R(Object data) {
        this(0, "success", data);
    }

    public R(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public static R error() {
        return error("未知异常请联系管理员.");
    }

    public static R error(String msg) {
        return error(500,msg);
    }

    public static R error(int code, String msg) {
        return new R(code,msg,null);
    }

    public static R success(String message) {
        return new R(message);
    }

    public static R success(Object data) {
        return new R(data);
    }

    public static R success(){
        return new R();
    }

    /**
     * @param keyValues  必须复数，键值对，data将被设为Map
     * @author  王俊辉
     * @date    2019年8月14日 下午5:36:24
     */
    public R put(Object... keyValues ) {
        initializeDataMap();
        for (int i = 0; i < keyValues.length; i+=2) {
            ((Map<String,Object>) (data)).put((String)keyValues[i],keyValues[i+1]);
        }
        return this;
    }

    public void initializeDataMap() {
        if (data == null || !(data instanceof Map)) {
            data = new HashMap<String, Object>();
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    /**
     * @param records
     * @param total
     * @return
     * @Exception
     * @author  王俊辉
     * @date    2019年9月28日 下午3:35:52
     */
    public R page(List<?> records, long total) {
        put("list",records,"total",total);
        return this;

    }


    /**
     * @return
     * @Exception
     * @author  王俊辉
     * @date    2019年9月29日 上午10:41:38
     */
    public R list(List<?> list) {
        put("list",list);
        return this;

    }
}

