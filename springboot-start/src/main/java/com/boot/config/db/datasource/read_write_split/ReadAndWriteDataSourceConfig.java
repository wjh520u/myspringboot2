package com.boot.config.db.datasource.read_write_split;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.boot.config.db.handler.SqlPrintInterceptor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@MapperScan(basePackages = {"com.boot.api.dao.mapper"}, sqlSessionTemplateRef  = "RW_sqlSessionTemplate")
public class ReadAndWriteDataSourceConfig {
	
	public static final ThreadLocal<String> dbSelect = new ThreadLocal<String>();
	
	 @Bean(name="roundRobinDataSouceProxy")
	 public AbstractRoutingDataSource roundRobinDataSouceProxy(
			 @Qualifier("dataSource") DataSource W_dataSource,
			 @Qualifier("R_dataSource") DataSource R_dataSource
			 ) {
	    	
	    	Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
	        //把所有数据库都放在targetDataSources中,注意key值要和determineCurrentLookupKey()中代码写的一至，
	        //否则切换数据源时找不到正确的数据源
	        targetDataSources.put("W", W_dataSource);
	        targetDataSources.put("R", R_dataSource);
	        
	        //路由类，寻找对应的数据源
	        AbstractRoutingDataSource proxy = new AbstractRoutingDataSource(){
	        	/**
	             * 这是AbstractRoutingDataSource类中的一个抽象方法，
	             * 而它的返回值是你所要用的数据源dataSource的key值，有了这个key值，
	             * targetDataSources就从中取出对应的DataSource，如果找不到，就用配置默认的数据源。
	             */
	        	@Override
	        	protected Object determineCurrentLookupKey() {
	        		String dbSelect2 = dbSelect.get();
	        		if (dbSelect2 == null) {
						dbSelect2 = "R";
					}
	        		System.out.println("选择库："+dbSelect2);
	        		return dbSelect2;
	        	}
	        };
	        
	        proxy.setDefaultTargetDataSource(W_dataSource);//默认库
	        proxy.setTargetDataSources(targetDataSources);
	        proxy.setLenientFallback(false);
	        return proxy;
	}
	
	@Bean(name = "RW_sqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("roundRobinDataSouceProxy") DataSource dataSource
    		,@Qualifier("RW_mybatisSqlSessionFactoryBean")MybatisSqlSessionFactoryBean bean) throws Exception {
    	
        return bean.getObject();
    }
	
	@Bean("RW_mybatisSqlSessionFactoryBean")
    @ConfigurationProperties(prefix = "mybatis-plus2")
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(@Qualifier("roundRobinDataSouceProxy") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        /**
         * 分页插件与打印sql
         */
        Interceptor[] interceptor = new Interceptor[]{new SqlPrintInterceptor(),new PaginationInterceptor()};
        bean.setPlugins(interceptor);
        
        //bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:resources/mysql/multi_mybatis_plus_xml/mybatis/mybatis_config.xml"));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return bean;
    }
	
	@Bean(name = "RW_transactionManager")
	public PlatformTransactionManager testTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
	    return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean(name = "RW_sqlSessionTemplate")
	public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("RW_sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
	    return new SqlSessionTemplate(sqlSessionFactory);
	}

    /*@Bean(name = "W_dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource W_dataSource( ) {
       return DruidDataSourceBuilder.create().build();
    }*/
    
    @Bean(name = "R_dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid2")
    public DataSource R_dataSource( ) {
       return DruidDataSourceBuilder.create().build();
    }
    
}
