package com.boot.config.db.datasource.read_write_split;

import java.util.Properties;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
public class ReadWriteTransactionAdviceConfig {
     private static final String AOP_POINTCUT_EXPRESSION = "execution (* com.boot.api.service..*.*(..))";
 
        @Autowired
        @Qualifier("RW_transactionManager")
        private PlatformTransactionManager transactionManager;
 
        @Bean("RWTransactionInterceptor")
        public TransactionInterceptor txAdvice() {
        	
        	/*DefaultTransactionAttribute txAttr_REQUIRED = new DefaultTransactionAttribute();
            txAttr_REQUIRED.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
 
            DefaultTransactionAttribute txAttr_REQUIRED_READONLY = new DefaultTransactionAttribute();
            txAttr_REQUIRED_READONLY.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            txAttr_REQUIRED_READONLY.setReadOnly(true);
 
            NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();*/
        	
            Properties transactionAttribute = new Properties();
            transactionAttribute.setProperty("*_W", "PROPAGATION_REQUIRED,-Throwable");
            transactionAttribute.setProperty("*_R", "PROPAGATION_SUPPORTS,-Throwable");
            
            return new TransactionInterceptor(transactionManager, transactionAttribute);
        }
 
        @Bean("RWAdvisor")
        public Advisor txAdviceAdvisor() {
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
            pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
            return new DefaultPointcutAdvisor(pointcut, txAdvice());
        }
}