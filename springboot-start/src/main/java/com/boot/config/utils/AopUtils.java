package com.boot.config.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.boot.config.web.configuration.GetBean;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;

public class AopUtils {

    public static String getExpressionValue(String expression,JoinPoint joinPoint,boolean canExpressionThrowNullException,boolean canNullValue) throws Exception {

        if (expression == null || expression.trim().equals("") ) {
            return "";
        }
        Object[] args = joinPoint.getArgs();
        StringBuilder returnString = new StringBuilder();
        String[] parameterNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        Method methodSignature = ((MethodSignature)joinPoint.getSignature()).getMethod();
        //解析加号表达式参数
        String[] lockParams = expression.split("(\\{|\\})");
        //表达式方法参数取值，
        for (int i = 0; i < lockParams.length; i++) {

            //没有#直接使用
            String ex = lockParams[i].trim();
            Object lockParam = null;
            //去掉第一个字符#后解析
            String[] expressions = ex.substring(1).split("\\.");
            String substring = ex.substring(0, 1);
            if ("#@".indexOf(substring) != -1) {
                switch (ex.substring(0, 1)) {
                    case "@":
                        lockParam = GetBean.get(expressions[0]);
                        break;
                    default:
                        //方法参数
                        lockParam = getParam(parameterNames, expressions[0],args);
                        break;
                }
                if (lockParam == null) {
                    throwException2(canNullValue,canExpressionThrowNullException,lockParams[i],methodSignature,joinPoint,returnString,expressions,expressions.length>1);
                }else {
                    //表达式解析，确定汇总值
                    for (int j = 1; j < expressions.length; j++) {
                        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(lockParam.getClass(), expressions[j]);
                        if (propertyDescriptor != null) {
                            //String methodName = "get"+expressions[j].substring(0,1).toUpperCase()+expressions[j].substring(1);
                            Method readMethod = propertyDescriptor.getReadMethod();//lockParam.getClass().getMethod(methodName);
                            lockParam = readMethod.invoke(lockParam);
                        }else {
                            Field field = lockParam.getClass().getDeclaredField(expressions[j]);
                            if (field.getModifiers() == 1) {
                                lockParam = field.get(lockParam);
                            }
                        }
                        if (lockParam == null) {
                            throwException2(canNullValue,canExpressionThrowNullException,lockParams[i],methodSignature,joinPoint,returnString,expressions,j != (expressions.length-1));
                            break;
                        }
                    }
                }

                //汇总锁字符串
                returnString .append(lockParam == null ? "null" :lockParam.toString());
            }else {
                returnString .append(ex.replace("'", ""));
            }


        }
        return returnString.toString();
    }



    /**
     * @param canNullValue
     * @param canExpressionThrowNullException
     * @param string
     * @param methodSignature
     * @param joinPoint
     * @param returnString
     * @param expressions
     * @Exception
     * @author  王俊辉
     * @date    2019年8月21日 上午10:39:07
     */
    private static void throwException2(boolean canNullValue, boolean canExpressionThrowNullException, String string,
                                        Method methodSignature, JoinPoint joinPoint, StringBuilder returnString, String[] expressions, Boolean isThrow) {
        if (!canNullValue) {
            throw new RuntimeException("执行表达式："+string+"  出现null值。\n"+methodSignature.toGenericString());
        }
        if (canExpressionThrowNullException) {
            if (isThrow) {
                throw new RuntimeException("执行表达式："+string+" >> "+expressions[0]+"  发生NullException异常。\n        at "
                        +joinPoint.getTarget().getClass().getName()+"."
                        +methodSignature.getName()
                        +"("+ joinPoint.getTarget().getClass().getSimpleName()+".java:0)");
            }
        }

    }

    private static Object getParam(String[] parameterNames, String string, Object[] args) throws Exception {
        for (int i = 0; i < parameterNames.length; i++) {
            if (string.equals(parameterNames[i])) {
                return args[i];
            }
        }
        throw new Exception("参数'"+string+"'"+"不存在。");
    }

    public static void main(String[] args) {
        String[] split = "ssnn{yy}sjoj".split("(\\{|\\})");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
    }
}
