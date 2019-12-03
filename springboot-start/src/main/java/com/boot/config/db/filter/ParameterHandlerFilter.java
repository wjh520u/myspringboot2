package com.boot.config.db.filter;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

@Intercepts({@Signature(type = ParameterHandler.class,method ="getParameterObject",args = {})})
public class ParameterHandlerFilter implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object proceed = invocation.proceed();
        return proceed;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("插件配置的初始化参数："+properties);
    }
}
