package com.wjh.shiro.config.configuaion;

import com.wjh.shiro.config.filter.RequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Web {

    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RequestFilter());
        registration.addUrlPatterns("/*");
        registration.setName("RequestFilter");
        registration.setOrder(1);
        return registration;
    }

}
