package com.wjh.shiro.config.filter;

import com.alibaba.fastjson.JSONObject;
import com.wjh.shiro.config.ShiroSession;
import com.wjh.shiro.config.entity.MyToken;
import com.wjh.shiro.config.entity.R;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;

public class TokenCreateFilter extends AuthenticatingFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }

        return false;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse servletResponse) throws Exception {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        if(token == null || token.equals("")){
            return null;
        }

        return new MyToken(token);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if(token == null || token.equals("")){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest)request).getHeader("Origin"));

            String json = JSONObject.toJSONString(R.error(HttpStatus.UNAUTHORIZED.value(), "invalid token"));

            httpResponse.getWriter().print(json);

            return false;
        }

        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest)request).getHeader("Origin"));
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();

            int code = HttpStatus.UNAUTHORIZED.value();

            if(throwable instanceof IncorrectCredentialsException) {
                code = 452;
            }

            R r = R.error(code, throwable.getMessage());

            String json = JSONObject.toJSONString(r);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader(ShiroSession.AUTH_TOKEN);

        //如果header中不存在token，则从参数中获取token
        if(token == null || token.equals("")){
            token = httpRequest.getParameter(ShiroSession.AUTH_TOKEN);
        }

        return token;
    }
}
