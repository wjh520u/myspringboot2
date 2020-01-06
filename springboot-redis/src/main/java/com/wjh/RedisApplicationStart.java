package com.wjh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.wjh")
public class RedisApplicationStart extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RedisApplicationStart.class, args);
    }

    // 打包启动本类
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(RedisApplicationStart.class);
    }

}
