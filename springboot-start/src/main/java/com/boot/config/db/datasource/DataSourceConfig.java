package com.boot.config.db.datasource;

import javax.sql.DataSource;

import com.boot.config.db.filter.MyFirstInterceptor;
import com.boot.config.db.filter.ParameterHandlerFilter;
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

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.boot.config.db.handler.SqlPrintInterceptor;


@Configuration
@MapperScan(basePackages = {"com.boot.api.dao.mapper"}, sqlSessionTemplateRef  = "sqlSessionTemplate")
public class DataSourceConfig {

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource testDataSource( ) {
       return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource
    		,@Qualifier("mybatisSqlSessionFactoryBean")MybatisSqlSessionFactoryBean bean) throws Exception {
    	
        return bean.getObject();
    }
    
    
    @Bean("mybatisSqlSessionFactoryBean")
    @ConfigurationProperties(prefix = "mybatis-plus")
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource); 
        /**
         * 分页插件与打印sql
         */
        Interceptor[] interceptor = new Interceptor[]{new SqlPrintInterceptor(),new PaginationInterceptor(),new MyFirstInterceptor(),new ParameterHandlerFilter()};
        bean.setPlugins(interceptor);
        
        //bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:resources/mysql/multi_mybatis_plus_xml/mybatis/mybatis_config.xml"));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return bean;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
