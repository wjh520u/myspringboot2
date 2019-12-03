/*
 * Project Name: superHorse3
 */
 
package com.wjh.shiro.config;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author  王俊辉
 * 2019年9月26日 上午10:19:11
 */
public class UuidSessionIdGenerator implements SessionIdGenerator{
    
    @Override
    public Serializable generateId(Session session) {
        Serializable uuid = new JavaUuidSessionIdGenerator().generateId(session);
        System.out.println("生成的sessionid是："+uuid);
        return uuid;
    }
 
}
