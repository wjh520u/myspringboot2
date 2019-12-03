package com.boot.config.db.filter;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import com.boot.config.utils.MapUtil;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
 * 查询返回List<Map>去下划线插件。
 *
 * @author  王俊辉
 * 2019年9月29日 下午1:47:33
 */
@Intercepts({@Signature(type = ResultSetHandler.class,method ="handleResultSets",args = Statement.class)})
public class MyFirstInterceptor implements Interceptor {

    /**
     * @Description 拦截目标对象的目标方法
     **/
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object object = invocation.proceed();
        if (object instanceof ArrayList<?>) {
            ArrayList resultList = (ArrayList) object;
            int isToRemoveUnderline = 0;
            if (resultList != null && resultList.size() > 0) {
                if (resultList.get(0) instanceof Map) {
                    Map map = (Map) resultList.get(0);
                    for (Object key : map.keySet()) {
                        if (((String) key).contains("_")) {
                            isToRemoveUnderline = 1;
                            break;
                        }
                    }
                }
            }
            if (isToRemoveUnderline == 1) {
                MapUtil.removeKeyUnderline(resultList);
            }
        }
            return object;
    }

    /**
     * @Description 包装目标对象 为目标对象创建代理对象
     * @Param target为要拦截的对象
     * @Return 代理对象
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 获取配置文件的属性
     **/
    @Override
    public void setProperties(Properties properties) {
        System.out.println("插件配置的初始化参数："+properties);
    }
}