package com.other.netty.tcp_http;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import com.other.netty.tcp_http.service.JsonHttpServer;
import com.other.netty.tcp_http.service.StringTcpServer;


//@SpringBootApplication(scanBasePackages = "com.other.netty.tcp_http")
//@EnableAutoConfiguration(exclude={DruidDataSourceAutoConfigure.class,DataSourceAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class,
//		HibernateJpaAutoConfiguration.class,JdbcTemplateAutoConfiguration.class,JndiDataSourceAutoConfiguration.class,XADataSourceAutoConfiguration.class})
public class NettyStart {
	
	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(NettyStart.class)
		.properties("spring.config.location=classpath:/other/application.yml").run(args);
	}
	
	//@Bean
	public StringTcpServer StringTcpServer() {
		return new StringTcpServer(8888);
	}
	
	@Bean
	public JsonHttpServer JsonHttpServer() {
		return new JsonHttpServer(8888);
	}

}
