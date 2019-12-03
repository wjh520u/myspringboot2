package com.wjh.shiro.config.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: vtams
 * @description:
 * @author: Mr.Wang
 * @create: 2019-10-11 17:55
 **/
public class RequestFilter implements Filter {

    public static ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();

    public static ThreadLocal<HttpServletResponse> response = new ThreadLocal<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        request.set((HttpServletRequest) servletRequest);
        response.set((HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
