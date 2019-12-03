package com.boot.config.utils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.boot.config.web.configuration.GetBean;

public class AopUtil3 {

	public static class TempString {
		public TempString() {
			// TODO Auto-generated constructor stub
		}
		public String tempString;
	}
	
	public static interface AopExetor {
		/**
		 * @作者：王俊辉
		 * 
		 * @param joinPoint
		 * @param param param[0]为返回值，表达式涉及到时要提供
		 * @return 
		 * 2019年5月30日上午11:37:24
		 */
		public Object executor(JoinPoint joinPoint, Object... param) throws Exception;
	}
	
	public static final AopExetor AOP_EXCEPT = new AopExetor(){
		@Override
		public Object executor(JoinPoint joinPoint, Object... param) throws Exception {
			return null;
		}
	};

	/**
	 * @作者：王俊辉
	 * 
	 * @param expression
	 * @param classSign
	 * @param joinPoint
	 * @param exe
	 * @param returnObject
	 * @param isReturnMustString  是否必须返回String类型，如果为false，表达式只支持{ex}，且不支持加号
	 * @return
	 * @throws Exception 
	 * 2019年5月31日上午9:19:50
	 */
	private static BeanFactoryResolver beanFactoryResolver = new BeanFactoryResolver(GetBean.ctx);
	//使用SPEL进行key的解析
	private static ExpressionParser parser = new SpelExpressionParser(new SpelParserConfiguration
    		(SpelCompilerMode.MIXED, AopUtil3.class.getClassLoader(), true, true, 5));
	
	public static AopUtil.AopExetor createAopExecutetor(String expression,String classSign, JoinPoint joinPoint,ConcurrentHashMap<Object, AopUtil.AopExetor> exe,Object returnObject,boolean isReturnMustString) throws Exception {
		Method methodSignature = ((MethodSignature)joinPoint.getSignature()).getMethod();
		StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(beanFactoryResolver);
		long currentTimeMillis2 = System.currentTimeMillis();
    	AopUtil.AopExetor aopExetor = new AopUtil.AopExetor() {
    		
    		StandardEvaluationContext context = getStandardEvaluationContext();
    		Expression parseExpression = new SpelExpressionParser(new SpelParserConfiguration
    	    		(SpelCompilerMode.MIXED, AopUtil3.class.getClassLoader(), true, true, 5)).parseExpression(expression);
    		
			@Override
			public Object executor(JoinPoint joinPoint, Object... param) throws Exception {
				
				Object[] args = joinPoint.getArgs();
		    	String[] parameterNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
		    	for (int i = 0; i < parameterNames.length; i++) {
					context.setVariable(parameterNames[i], args[i]);
				}
		    	Object value = parseExpression.getValue(context);
				
				return value != null ? value.toString():"";
			}
			
			StandardEvaluationContext getStandardEvaluationContext(){
				StandardEvaluationContext context = new StandardEvaluationContext();
				context.setBeanResolver(beanFactoryResolver);
				context.setRootObject(joinPoint.getThis());
				return context;
			}
		};

		exe.put(methodSignature, aopExetor);
		//System.out.println("生成输出："+alogExetor.executor(joinPoint,returnObject));
		System.out.println(classSign+":"+(System.currentTimeMillis()-currentTimeMillis2));
		return aopExetor;
	}
	
}
