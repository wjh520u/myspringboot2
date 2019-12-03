/*
 * Project Name: superHorse3
 */
 
package com.wjh.shiro.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.wjh.shiro.config.filter.CORSAuthenticationFilter;
import com.wjh.shiro.config.filter.IAccessControlFilter;
import com.wjh.shiro.config.filter.KickoutSessionControlFilter;
import com.wjh.shiro.config.filter.TokenCreateFilter;
import com.wjh.shiro.config.serializer.FastJsonSerializer;
import com.wjh.shiro.config.serializer.KryoSerializer;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.crazycake.shiro.serializer.ObjectSerializer;
import org.crazycake.shiro.serializer.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：LX
 * 创建时间： 2019/5/27. 11:39
 * 地点：广州
 * 目的: shiro配置
 * 备注说明：
 */
@Configuration
public class ShiroConfig {

    private static Logger log = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * 对shiro的拦截器进行注入
     * <p>
     * securityManager:
     * 所有Subject 实例都必须绑定到一个SecurityManager上,SecurityManager 是 Shiro的核心，初始化时协调各个模块运行。然而，一旦 SecurityManager协调完毕，
     * SecurityManager 会被单独留下，且我们只需要去操作Subject即可，无需操作SecurityManager 。 但是我们得知道，当我们正与一个 Subject 进行交互时，实质上是
     * SecurityManager在处理 Subject 安全操作
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        //设置遇到未登录、未授权等情况时候，请求这些地址，返回相应的错误
        shiroFilter.setLoginUrl("/user/shiroError?errorId=" + 1);
        shiroFilter.setUnauthorizedUrl("/user/shiroError?errorId=" + 1);

        //自定义拦截器
        Map<String, Filter> customFilterMap = new LinkedHashMap<>();
        //customFilterMap.put("corsAuthenticationFilter", new CORSAuthenticationFilter());
        //customFilterMap.put("access", new IAccessControlFilter());
        customFilterMap.put("mytoken", new TokenCreateFilter());
        shiroFilter.setFilters(customFilterMap);

        //拦截器，配置访问权限 必须是LinkedHashMap，因为它必须保证有序。滤链定义，从上向下顺序执行，一般将 /**放在最为下边
        Map<String, String> filterMap = new LinkedHashMap<String, String>();

        // 配置不会被拦截的链接 顺序判断
        filterMap.put("/login", "anon");
        filterMap.put("/user/shiroError", "anon");
        filterMap.put("/user/reg", "anon");

        //剩余的请求shiro都拦截
        filterMap.put("/**", "authc,mytoken");

        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    /**
     * securityManager 核心配置
     * 安全控制层
     * @return
     */
    @Bean
    public org.apache.shiro.mgt.SecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //设置自定义的realm
        defaultWebSecurityManager.setRealm(myRealm());
        //自定义的shiro session 缓存管理器
        defaultWebSecurityManager.setSessionManager(sessionManager());
        //将缓存对象注入到SecurityManager中
        defaultWebSecurityManager.setCacheManager(cacheManager());
        defaultWebSecurityManager.setSubjectFactory(new SysDefaultWebSubjectFactory());

        return defaultWebSecurityManager;
    }

    /**
     * 自定义的realm
     * @return
     */
    @Bean
    public ShiroRealm myRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCacheManager(cacheManager());
        shiroRealm.setCachingEnabled(true);
        shiroRealm.setAuthorizationCacheName("TOKEN_PREMISSION");
        shiroRealm.setAuthenticationCacheName("TOKEN_SESSION");
        return shiroRealm;
    }

    /**
     * shiro缓存管理器;
     * 需要添加到securityManager中
     * @return
     */
    /*@Bean
    public RedisCacheManager cacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //redis中针对不同用户缓存
        //用户权限信息缓存时间
        redisCacheManager.setExpire(200000);
        return redisCacheManager;
    }

    private RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("127.0.0.1");
        redisManager.setPort(6379);

        //配置缓存过期时间
        redisManager.setTimeout(99999);
        return redisManager;
    }*/
    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    /*@Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setSessionIdGenerator(new UuidSessionIdGenerator());
        return redisSessionDAO;
    }*/

    /**
     * shiro缓存管理器
     * 1 添加相关的maven支持
     * 2 注册这个bean，将缓存的配置文件导入
     * 3 在securityManager 中注册缓存管理器，之后就不会每次都会去查询数据库了，相关的权限和角色会保存在缓存中，但需要注意一点，更新了权限等操作之后，需要及时的清理缓存
     */
    @Bean
    public EhCacheManager cacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }

    /**
     * 自定义的 shiro session 缓存管理器，用于跨域等情况下使用 token 进行验证，不依赖于sessionId
     * @return
     */
    @Bean
    public SessionManager sessionManager(){
        //将我们继承后重写的shiro session 注册
        ShiroSession shiroSession = new ShiroSession();
        //如果后续考虑多tomcat部署应用，可以使用shiro-redis开源插件来做session 的控制，或者nginx 的负载均衡
        SessionCache enterpriseCacheSessionDAO = new SessionCache();
        enterpriseCacheSessionDAO.setSessionIdGenerator(new UuidSessionIdGenerator());
        enterpriseCacheSessionDAO.setCacheManager(cacheManager());
        enterpriseCacheSessionDAO.setActiveSessionsCacheName("TOKEN_USER");
        shiroSession.setSessionDAO(enterpriseCacheSessionDAO);
        return shiroSession;    
    }

    /**
     * 限制同一账号登录同时登录人数控制
     *
     * @return KickoutSessionControlFilter
     */
    @Bean
    public KickoutSessionControlFilter kickoutSessionControlFilter() {
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        kickoutSessionControlFilter.setCacheManager(cacheManager());
        kickoutSessionControlFilter.setSessionManager(sessionManager());
        kickoutSessionControlFilter.setKickoutAfter(false);
        kickoutSessionControlFilter.setMaxSession(10);
        kickoutSessionControlFilter.setKickoutUrl( "/external/page/kickout");
        return kickoutSessionControlFilter;
    }
    /**
     * Shiro生命周期处理器
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /***
     * 授权所用配置
     *
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
    /**
     * 开启shiro 的AOP注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
