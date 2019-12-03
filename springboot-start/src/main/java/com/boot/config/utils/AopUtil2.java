package com.boot.config.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.boot.config.web.configuration.GetBean;
import com.project.utils.RegexTool;

public class AopUtil2 {

	public static class TempString {
		public TempString () {
			// TODO Auto-generated constructor stub
		}

		public String tempString;
	}

	public static interface AopExetor {
		/**
		 * @作者：王俊辉
		 * 
		 * @param joinPoint
		 * @param param
		 *            param[0]为返回值，表达式涉及到时要提供
		 * @return 2019年5月30日上午11:37:24
		 */
		public Object executor(JoinPoint joinPoint, Object... param) throws Exception;
	}

	public static final AopExetor AOP_EXCEPT = new AopExetor() {
		@Override
		public Object executor( JoinPoint joinPoint, Object... param ) throws Exception {
			return null;
		}
	};
	
	/**
	 * @作者：王俊辉
	 * 
	 * 获取方法AOP表达式执行器
	 * 
	 * @param expression  表达式
	 * @param classSign 编译后缀
	 * @param joinPoint AOP切点
	 * @param exe 缓存器Map
	 * @param isReturnMustString
	 *            是否必须返回String类型，如果为false，不支持加号
	 * @return
	 * @throws Exception 
	 * 2019年8月13日下午2:19:29
	 */
	public static AopExetor getExetor( String expression, String classSign, ProceedingJoinPoint joinPoint,
			ConcurrentHashMap< Object, AopExetor > exe, boolean isReturnMustString ) throws Exception {

		Method methodSignature = ( (MethodSignature) joinPoint.getSignature() ).getMethod();
		AopExetor aopExetor = exe.get( methodSignature );
		if ( aopExetor == null ) {
			synchronized ( ( methodSignature + classSign ).intern() ) {
				if ( exe.get( methodSignature ) == null ) {
					// 编译脚本
					aopExetor = AopUtil2.createAopExecutetor( expression, classSign, joinPoint, exe, true );
				} else {
					aopExetor = exe.get( methodSignature );
				}
			}
		}
		return aopExetor;
	}

	/**
	 * @作者：王俊辉
	 * 
	 * @param expression
	 * @param classSign
	 * @param joinPoint
	 * @param exe
	 * @param returnObject
	 * @param isReturnMustString
	 *            是否必须返回String类型，如果为false，不支持加号
	 * @return
	 * @throws Exception
	 *             2019年5月31日上午9:19:50
	 */
	public static AopExetor createAopExecutetor( String expression, String classSign, JoinPoint joinPoint,
			ConcurrentHashMap< Object, AopExetor > exe, boolean isReturnMustString ) throws Exception {
		Method methodSignature = ( (MethodSignature) joinPoint.getSignature() ).getMethod();
		Object [] args = joinPoint.getArgs();
		String [] parameterNames = ( (MethodSignature) joinPoint.getSignature() ).getParameterNames();

		// expression = preAnaly(parameterNames,expression);
		String expressionNew = expression;
		long currentTimeMillis2 = System.currentTimeMillis();
		// 解析加号表达式参数
		String [] addExs = expressionNew.split( "\\+" );
		for ( int i = 0; i < addExs.length; i++ ) {
			TempString tempString = new TempString();
			tempString.tempString = addExs[i];
			// 获取表达式值
			AopUtil2.getExpressionObject( methodSignature, parameterNames, addExs[i].trim(), args, joinPoint,
					tempString );
			expressionNew = expressionNew.replace( addExs[i], tempString.tempString );
		}
		// 是否返回表达式类型，而不是String
		String targetClassName = joinPoint.getTarget().getClass().getName();
		String methodBody = "\n" + getPropertiesString( joinPoint ) + "Object rs = null;try{ rs = " + expressionNew
				+ ";}catch (Exception e) " + "{throw new Exception(\"" + "执行" + classSign
				+ "语法异常  : \"+e.getClass().getName()+\".e(\"+e.getClass().getName().substring(e.getClass().getName().lastIndexOf(\".\")+1)+\".java:1) cause:\"+e.getMessage()+"
				+ "\"	at " + targetClassName + "." + methodSignature.getName() + "("
				+ targetClassName.substring( targetClassName.lastIndexOf( "." ) + 1 ) + ".java:1)\");}" + "return rs;"
				+ "\n";

		// System.out.println("方法："+methodBody);
		AopExetor alogExetor = createClassAndInitialize( methodBody, joinPoint, methodSignature, classSign );
		exe.put( methodSignature, alogExetor );
		// System.out.println("生成输出："+alogExetor.executor(joinPoint,returnObject));
		System.out.println( classSign + ":" + ( System.currentTimeMillis() - currentTimeMillis2 ) );
		return alogExetor;
	}

	public static void getExpressionObject( Method methodSignature, String [] parameterNames, String expression,
			Object [] args, JoinPoint joinPoint, TempString tempString ) throws Exception {

		tempString.tempString = replaceField( tempString.tempString, joinPoint );

		for ( int i = 0; i < parameterNames.length; i++ ) {
			if ( expression.indexOf( parameterNames[i] ) != -1 ) {
				String paramEx = "#" + parameterNames[i];
				if ( expression.indexOf( paramEx ) != -1 ) {
					Class< ? > paramClass = methodSignature.getParameterTypes()[i];
					tempString.tempString = tempString.tempString.replace( paramEx,
							"((" + paramClass.getName() + ")" + "args[" + i + "])" );
				}
			}
		}

		if ( tempString.tempString.indexOf( "#this" ) != -1 ) {
			tempString.tempString = tempString.tempString.replace( "#this", "target" );
		}

		if ( tempString.tempString.indexOf( "#return" ) != -1 ) {
			tempString.tempString = tempString.tempString.replace( "#return",
					"((" + methodSignature.getReturnType().getName() + ")" + "(param[0]))" );
		}

		List< String > mStrings = RegexTool.matchAll( "(\\$[a-zA-z0-9]{2,})", tempString.tempString );
		for ( String string : mStrings ) {
			Class< ? > object = GetBean.get( string.substring( 1 ) ).getClass();
			tempString.tempString = tempString.tempString.replace( string,
					"((" + object.getName().substring( 0, object.getName().indexOf( "$$" ) ) + ")" + "("
							+ GetBean.class.getName() + ".get(\"" + string.substring( 1 ) + "\")))" );
		}

		tempString.tempString = tempString.tempString.replace( "'", "\"" );

	}

	public static AopExetor createClassAndInitialize( String methodBody, JoinPoint joinPoint, Method methodSignature,
			String classSign ) throws Exception {

		ClassPool pool = ClassPool.getDefault();
		CtClass makeClass = pool.makeClass( joinPoint.getTarget().getClass().getName() + "." +System.currentTimeMillis()+ methodSignature.getName()
				+ methodSignature.hashCode() + "_" + classSign );
		makeClass.setInterfaces( new CtClass[] { pool.getCtClass( AopExetor.class.getName() ) } );
		System.out.println( "public Object executor(" + JoinPoint.class.getName()
				+ " joinPoint, Object... param) throws Exception{" + methodBody + "}" );
		CtMethod ctMethod = CtMethod.make( "public Object executor(" + JoinPoint.class.getName()
				+ " joinPoint , Object[] param) throws Exception{" + methodBody + "}", makeClass );
		makeClass.addMethod( ctMethod );
		// 转换成Class
		Class< ? > gc = makeClass.toClass();
		// 将该CtClass从ClassPool中移除，
		makeClass.detach();
		// 调用方法
		AopExetor alogExetor = (AopExetor) gc.newInstance();
		return alogExetor;
	}

	public static String getPropertiesString( JoinPoint joinPoint ) {
		Object object = joinPoint.getTarget();
		String className = object.getClass().getName();
		String string = "java.lang.Object[] args=joinPoint.getArgs();" + className + " target=(" + className
				+ ")joinPoint.getTarget();";
		return string;
	}

	public static void main( String [] args ) {
		String mat = "@soojs#test.sjihuhdi55jidinRRuh.uh55u(g('')";// "(\\#[a-zA-z0-9\\.\\(\\)('[.]{1,}')]{2,})
		System.out.println( RegexTool.matchAll(
				"((\\#([a-zA-z0-9]{1,})\\.[a-zA-z0-9\\.]{1,})|(\\@([a-zA-z0-9]{1,})\\.[a-zA-z0-9\\.]{1,}))", mat ) );
	}

	public static String replaceField( String source, JoinPoint joinPoint ) throws Exception {
		List< String > matchAll = RegexTool.matchAll(
				"((\\#([a-zA-z0-9]{1,})\\.[a-zA-z0-9\\.]{1,})|(\\@([a-zA-z0-9]{1,})\\.[a-zA-z0-9\\.]{1,}))", source );

		Method methodSignature = ( (MethodSignature) joinPoint.getSignature() ).getMethod();

		for ( String ex : matchAll ) {
			int exStart = source.indexOf( ex );
			String rp = ex;
			if ( source.substring( exStart + ex.length() ).trim().startsWith( "(" ) ) {
				rp = ex.substring( 0, ex.lastIndexOf( "." ) );
			}
			Class< ? > startClass = null;
			String [] exSplit = rp.split( "\\." );
			switch ( rp.substring( 0, 1 ) ) {
			case "#" :
				String startName = exSplit[0].replace( "#", "" );
				String [] parameterNames = ( (MethodSignature) joinPoint.getSignature() ).getParameterNames();
				for ( int i = 0; i < parameterNames.length; i++ ) {
					if ( startName.equals( parameterNames[i] ) ) {
						startClass = methodSignature.getParameterTypes()[i];
						break;
					}
				}
				break;
			case "@" :
				String startName2 = exSplit[0].replace( "@", "" );
				Object object = GetBean.get( startName2 );
				startClass = object.getClass();
				break;
			default :
				throw new Exception( "不支持该语法：" + exSplit[0] );
			}
			source = source.replace( ex, getReplaceFieldString( ex, startClass, exSplit ) );
		}

		return source;

	}

	private static String getReplaceFieldString( String ex, Class< ? > startClass, String [] exSplit )
			throws Exception {
		Field declaredField = null;
		for ( int i = 1; i < exSplit.length; i++ ) {
			declaredField = startClass.getDeclaredField( exSplit[i] );
			switch ( declaredField.getModifiers() ) {
			case 2 :
				if ( i != ( exSplit.length - 1 ) ) {
					ex = ex.replace( "." + exSplit[i] + ".",
							".get" + exSplit[i].substring( 0, 1 ).toUpperCase() + exSplit[i].substring( 1 ) + "." );
				} else {
					ex = ex.replace( "." + exSplit[i],
							".get" + exSplit[i].substring( 0, 1 ).toUpperCase() + exSplit[i].substring( 1 ) + "()" );
				}
				break;
			}
		}
		return ex;
	}

}
