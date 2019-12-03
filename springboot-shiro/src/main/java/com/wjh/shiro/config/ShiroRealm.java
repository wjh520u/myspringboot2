/*
 * Project Name: superHorse3
 */
 
package com.wjh.shiro.config;

import com.wjh.shiro.config.entity.MyToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.boot.config.shiro.entity.User;

import java.util.*;

public class ShiroRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return (token instanceof MyToken);
    }

    /**
     * 获取用户角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        Object primaryPrincipal = principal.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        HashSet<String> arrayList = new HashSet<>();
        arrayList.add("test");
        info.setStringPermissions(arrayList);
        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        String password = (String) token.getCredentials();

        System.out.println("用户" + userName + "认证-----ShiroRealm.doGetAuthenticationInfo");
        /*if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        if (user.getStatus().equals("0")) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }*/
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo("userName", password, getName());
        return info;
    }

}