package com.wjh.shiro.controller;

import com.wjh.shiro.config.entity.MyToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.AbstractValidatingSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    SessionManager sessionManager;

    @Autowired
    DefaultWebSecurityManager securityManager;

    @RequestMapping("/login")
    public Object login(String username, String pwd){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String id = (String) session.getId();
        MyToken usernamePasswordToken = new MyToken(id);
        subject.login(usernamePasswordToken);
        return subject.getSession().getId();
    }

    @RequestMapping("test")
    @RequiresPermissions("test")
    public Object test(){
        Session session = SecurityUtils.getSubject().getSession();
        System.out.println("???"+session);
        return "OK";
    }

}
