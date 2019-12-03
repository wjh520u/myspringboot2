package com.wjh.shiro.config;

import com.wjh.shiro.config.filter.RequestFilter;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class SessionCache extends EnterpriseCacheSessionDAO {
    @Override
    protected void cache(Session session, Serializable sessionId) {
        HttpServletRequest httpServletRequest = RequestFilter.request.get();
        if (httpServletRequest.getRequestURI().equals("/login")){
            final Object user =     session.getAttribute("user");
            if (user != null){
                System.out.println(user);
            }
        }
        super.cache(session, sessionId);
    }
}
