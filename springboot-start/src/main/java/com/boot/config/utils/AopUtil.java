package com.boot.config.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.boot.config.web.configuration.GetBean;
import com.project.utils.RegexTool;

public class AopUtil {

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
	public static AopExetor createAopExecutetor(String expression,String classSign, JoinPoint joinPoint,ConcurrentHashMap<Object, AopExetor> exe,Object returnObject,boolean isReturnMustString) throws Exception {
		Method methodSignature = ((MethodSignature)joinPoint.getSignature()).getMethod();
    	Object[] args = joinPoint.getArgs();
    	String[] parameterNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
    	
		expression = preAnaly(parameterNames,expression);
		String expressionNew = expression;
    	List<String> matchStrings = RegexTool.matchStrings( expression,"{","}");
    	if (matchStrings.size()>0) {
			long currentTimeMillis2 = System.currentTimeMillis();
    		for (String string : matchStrings) {
    			//解析加号表达式参数
    			String[] addExs = string.split("\\+");
    			for (int i = 0; i < addExs.length; i++) {
    				TempString tempString = new TempString();
    				tempString.tempString = addExs[i];
        			//获取表达式值
    				Object object = AopUtil.getExpressionObject(methodSignature,parameterNames,addExs[i].trim(),args,joinPoint,tempString);
    				expressionNew = expressionNew.replace(addExs[i], tempString.tempString);
				}
    		}
    		//是否返回表达式类型，而不是String
    		if (!isReturnMustString && matchStrings.size() == 1) {
				expressionNew = expressionNew.replace("{", "").replace("}", "");
			}else {
	    		expressionNew = "\""+expressionNew.replace("{", "\"+").replace("}", "+\"")+"\"";
			}
    		String targetClassName = joinPoint.getTarget().getClass().getName();
			String methodBody = "\n"+AopUtil.getPropertiesString(joinPoint)+
	    			"Object rs = null;try{ rs = "+expressionNew+";}catch (Exception e) "
	    					+ "{throw new Exception(\""
	    					+"执行"+classSign+"语法异常  : \"+e.getClass().getName()+\".e(\"+e.getClass().getName().substring(e.getClass().getName().lastIndexOf(\".\")+1)+\".java:1) cause:\"+e.getMessage()+"
	    							+"\"	at "+targetClassName+"."+methodSignature.getName()
	    							+ "("+targetClassName.substring(targetClassName.lastIndexOf(".")+1)+".java:1)\");}"
	    					+"return rs;"+"\n";
			
			//System.out.println("方法："+methodBody);
			AopExetor alogExetor = AopUtil.createClassAndInitialize(methodBody,joinPoint,methodSignature,classSign);
			exe.put(methodSignature, alogExetor);
			//System.out.println("生成输出："+alogExetor.executor(joinPoint,returnObject));
			System.out.println(classSign+":"+(System.currentTimeMillis()-currentTimeMillis2));
			return alogExetor;
		}
		return null;
	}


	public static Object getExpressionObject(Method methodSignature, String[] parameterNames, String expression, Object[] args, JoinPoint joinPoint, TempString tempString) throws Exception {

		//切分属性调用
		String[] expressions = expression.split("\\.");
		//expressions[0]代表开始值 如：'user.id'，则代表'user'
		if (expressions[0].equals("")) {
			throw new Exception("参数表达式错误。>> '"+expression+"'");
		}
		//获取expressions[0]表达值
		ClassP startObjectClass = new ClassP();
		startObjectClass.class1 = getTheStartObject(expressions[0],args,parameterNames,methodSignature,joinPoint,tempString);
		//表达式解析，确定汇总属性值
		for (int j = 1; j < expressions.length; j++) {
			startObjectClass.class1 = getObjectFieldOrMethodObject(startObjectClass,expressions[j],args,parameterNames,joinPoint,methodSignature,tempString);
		}
		return startObjectClass;
	}

	private static Class<?> getObjectFieldOrMethodObject(ClassP startObjectClass, String string, Object[] args, String[] parameterNames, JoinPoint joinPoint, Method methodSignature, TempString tempString) throws Exception {
		
		//解析实体方法调用
		if (string.indexOf("(") != -1) {
			String methodName = string.substring(0, string.indexOf("("));
			String matchString = RegexTool.matchOneStrings(string,"(",")");
			List<Class> classes = new ArrayList<Class>();
			if (matchString != null && !matchString.equals("")) {
				String[] methodParams = matchString.split(",");
				for (int i = 0; i < methodParams.length; i++) {
					if (!methodName.equals("")) {
						ClassP class1 = new ClassP();
						getObjectMethodParams(methodParams[i],args,parameterNames,class1,joinPoint,methodSignature,tempString);
						classes.add(class1.class1);
					}
				}
			}
			if (classes .size() == 0) {
				startObjectClass.class1 = startObjectClass.class1.getMethod(methodName).getReturnType();
			}else {
				Class<?>[] classess = new Class<?>[classes.size()];
				classes.toArray(classess);
				startObjectClass.class1 = startObjectClass.class1.getMethod(methodName, classess).getReturnType();
			}
		}
		//普通属性获取
		else {
			if (startObjectClass != null) {
				getEntityFieldObject(startObjectClass,string,tempString,".");
			}
		}
		return startObjectClass.class1;
	}

	

	private static void getObjectMethodParams(String methodParam, Object[] args, String[] parameterNames, ClassP class1, JoinPoint joinPoint, Method methodSignature, TempString tempString) throws Exception {
		switch (methodParam.trim().substring(0, 1)) {
		
		//解析字符串类型
		case "'":
			class1.class1 = String.class;
			break;
		case "[":
			int indexOf = methodParam.indexOf("]");
			String classNameT = methodParam.substring(methodParam.indexOf("[")+1, indexOf);
			String className = classNameT.trim().replace("-", ".");
			Class<?> forName = Class.forName(className);
			String substring2 = methodParam.substring(indexOf+1);
			tempString.tempString = tempString.tempString.replace("["+classNameT+"]", "("+className+")").replace(substring2, "("+substring2+")");
			if (methodParam.indexOf("<") != -1) {
				String substring = methodParam.substring(methodParam.indexOf("<")+1,methodParam.indexOf(">"));
				String[] expressions = substring.split(":");
				int paramIndex = getParamIndex(parameterNames,expressions[0]);
				class1.class1 = getParamClass(parameterNames,expressions[0],methodSignature);
				int indexOf2 = substring.indexOf(":");
				if (indexOf2 != -1) {
					tempString.tempString = tempString.tempString.replace( substring.substring(0, substring.indexOf(":")+1) ,"(("+class1.class1.getName()+")"+"(args["+paramIndex+"])):");
				}else {
					tempString.tempString = tempString.tempString.replace( substring ,"(("+class1.class1.getName()+")"+"(args["+paramIndex+"]))");
				}
				for (int j = 1; j < expressions.length; j++) {
					getEntityFieldObject(class1,expressions[j],tempString,":");
				}
				tempString.tempString = tempString.tempString.replace("<","").replace(">", "");
			}
			class1.class1 = forName;
			break;
		//解析方法参数类型
		case "<":
			String substring = methodParam.substring(methodParam.indexOf("<")+1,methodParam.indexOf(">"));
			String[] expressions = substring.split(":");
			int paramIndex = getParamIndex(parameterNames,expressions[0]);
			class1.class1 = getParamClass(parameterNames,expressions[0],methodSignature);
			tempString.tempString = tempString.tempString.replace(substring,"(("+class1.class1.getName()+")"+"(args["+paramIndex+"]))");
			//表达式解析，确定汇总属性值
			for (int j = 1; j < expressions.length; j++) {
				getEntityFieldObject(class1,expressions[j],tempString,":");
			}
			tempString.tempString = tempString.tempString.replace("<","").replace(">", "");
			break;
		default:
			//解析数字类型
			switch (methodParam.substring(methodParam.length()-1)) {
			case "f":
				tempString.tempString = tempString.tempString.replace(methodParam,"new Float("+methodParam+")");
				class1.class1 = Float.class;
				break;
			case "l":
				tempString.tempString = tempString.tempString.replace(methodParam,"new Long("+methodParam+")");
				class1.class1 = Long.class;
				break;
			case "d":
				tempString.tempString = tempString.tempString.replace(methodParam,"new Double("+methodParam+")");
				class1.class1 = Double.class;
				break;
			default:
				tempString.tempString = tempString.tempString.replace(methodParam,"new Integer("+methodParam+")");
				class1.class1 = Integer.class;
				break;
			}
			break;
		}
		return ;
	}
	
	private static void getEntityFieldObject( ClassP class1, String fieldName, TempString tempString, String sp) throws Exception {
		Field declaredField = class1.class1.getDeclaredField(fieldName);
		class1.class1 = declaredField.getType();
		switch (declaredField.getModifiers()) {
		case 1:
			tempString.tempString = tempString.tempString.replace(sp+fieldName, "."+fieldName);
			break;
		case 2:
			String methodName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
			tempString.tempString = tempString.tempString.replace(sp+fieldName, "."+methodName+"()");
			break;
		default:
			break;
		};
	}

	private static Class<?> getParamClass(String[] parameterNames,String string, Method methodSignature) {
		Class<?>[] parameterTypes = methodSignature.getParameterTypes();
		for (int i = 0; i < parameterNames.length; i++) {
			if (string.equals(parameterNames[i])) {
				return parameterTypes[i];
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

	private static Class<?> getTheStartObject(String string, Object[] args, String[] parameterNames, Method methodSignature, JoinPoint joinPoint, TempString tempString) throws Exception {
		Class<?> object= null;
		switch (string.substring(0,1)) {
		case "$":
			object = GetBean.get(string.substring(1)).getClass();
			tempString.tempString = tempString.tempString.replace(string,"(("+object.getName().substring(0, object.getName().indexOf("$$"))+")"+
					"("+GetBean.class.getName()+".get(\""+string.substring(1)+"\")))");
			break;
		
		case "#":
			String properties = string.substring(1);
			switch (properties) {
			case "return":
				tempString.tempString = tempString.tempString.replace(string,"(("+methodSignature.getReturnType().getName()+")"+
						"(param[0]))");
				object = methodSignature.getReturnType();
				break;
			case "this":
				tempString.tempString = tempString.tempString.replace(string,"target");
				object = joinPoint.getTarget().getClass();
				break;

			default:
				break;
			}
			break;

		default:
			int paramIndex = getParamIndex(parameterNames,string);
			Class<?> paramClass = getParamClass(parameterNames, string, methodSignature);
			tempString.tempString = tempString.tempString.replaceFirst(string,"(("+paramClass.getName()+")"+"args["+paramIndex+"])");
			object = paramClass;
			break;
		}
		
		return object;
	}

	private static int getParamIndex(String[] parameterNames, String string) throws Exception {
		for (int i = 0; i < parameterNames.length; i++) {
			if (string.equals(parameterNames[i])) {
				return i;
			}
		}
		throw new Exception("参数'"+string+"'"+"不存在。");
	}
	
	public static AopExetor createClassAndInitialize(String methodBody, JoinPoint joinPoint, Method methodSignature, String classSign) throws Exception {
		
		ClassPool pool = ClassPool.getDefault(); 
		CtClass makeClass = pool.makeClass(joinPoint.getTarget().getClass().getName()+"."+methodSignature.getName()+methodSignature.hashCode()+"_"+classSign);
		makeClass.setInterfaces(new CtClass[]{pool.getCtClass(AopExetor.class.getName())});
		System.out.println("public Object executor("+JoinPoint.class.getName()+" joinPoint, Object... param) throws Exception{"+methodBody+"}");
		CtMethod ctMethod = CtMethod.make(
				"public Object executor("+JoinPoint.class.getName()+" joinPoint , Object[] param) throws Exception{"+methodBody+"}",
				makeClass);
		makeClass.addMethod(ctMethod);
		// 转换成Class  
	    Class<?> gc = makeClass.toClass();  
	    // 将该CtClass从ClassPool中移除，  
	    makeClass.detach();
	    // 调用方法  
	    AopExetor alogExetor = (AopExetor) gc.newInstance();  
		return alogExetor;
	}
	
	public static String getPropertiesString(JoinPoint joinPoint) {
		Object object = joinPoint.getTarget();
		String className = object.getClass().getName();
		String string = "java.lang.Object[] args=joinPoint.getArgs();"+
				className+" target=("+className+")joinPoint.getTarget();";
		return string;
	}
	

	private static String preAnaly(String[] parameterNames, String expression) throws Exception {
		List<String> matchStrings = RegexTool.matchStrings( expression,"[", "]");
		for (String string : matchStrings) {
			expression = expression.replace(string, string.replace(".", "-"));
		}
		List<String> matchStrings2 = RegexTool.matchStrings( expression,"(",")");
		
		for (String string : matchStrings2) {
			String[] splits = string.split(",");
			for (int i = 0; i < splits.length; i++) {
				int start = splits[i].indexOf("]") == -1 ? 0 : splits[i].indexOf("]")+1;
				int end = splits[i].indexOf(".") == -1 ? splits[i].length()-1 : splits[i].indexOf(".");
				String substring = splits[i].substring(start, end);
				for (int j = 0; j < parameterNames.length; j++) {
					if (substring.trim().equals(parameterNames[j])) {
						String substring2 = splits[i].substring(start);
						expression = expression.replace(string, string.replace(substring2, "<"+substring2.trim().replace(".", ":")+">"));
					}
				}
			}
		}
		return expression;
	}
}

class ClassP{
	public Class<?> class1;
}
