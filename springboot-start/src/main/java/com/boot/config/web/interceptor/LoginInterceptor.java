package com.boot.config.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.boot.config.properties.annotation.session.IgnoreLogin;
import com.boot.config.properties.annotation.session.IgnorePermission;
import com.boot.config.properties.annotation.session.Login;
import com.boot.config.properties.annotation.session.Permission;
import com.project.utils.TF;

/**
 * 权限，Token验证,
 * 
 * @author huangxianghan
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler )
			throws Exception {
		boolean toValid = false;
		IgnoreLogin annotation;
		// 判断是否是调用Controller，避免拦截静态资源
		if ( handler instanceof HandlerMethod ) {
			// 获取类上的 IgnoreLogin注解 ，该注解标记忽略登录
			annotation = ( (HandlerMethod) handler ).getBeanType().getAnnotation( IgnoreLogin.class );
			// 如果类上没有 IgnoreLogin注解，则判断Controller方法上有无 IgnoreLogin注解
			if ( annotation == null ) {
				toValid = true;
				IgnoreLogin loginIgnore = ( (HandlerMethod) handler ).getMethodAnnotation( IgnoreLogin.class );
				if ( loginIgnore != null ) {
					toValid = false;
				}
			}
			// 如果类上有 IgnoreLogin注解 ，则需实际判断方法上有无特殊需要的 Login登录注解标记
			else {
				Login loginAnnotation = ( (HandlerMethod) handler ).getMethodAnnotation( Login.class );
				if ( loginAnnotation != null ) {
					toValid = true;
				} else {
					toValid = false;
				}
			}
		} else {
			return true;
		}

		// 无需登录，直接通过
		if ( toValid == false ) {
			// System.out.println("无需登录");
			return true;
		}

		// 从header中获取token
		String token = request.getHeader( "token" );
		// 如果header中不存在token，则从参数中获取token
		if ( TF.isBlank( token ) ) {
			token = request.getParameter( "token" );
		}

		// 如果token为空，直接返回
		if ( TF.isBlank( token ) ) {
			System.out.println( "无token"+request.getRequestURI() );
			if ( !request.getRequestURI().startsWith( "/error" ) ) {
				System.out.println( "无token2"+request.getRequestURI() );
				// throw new JsonException(403,"没有权限。");
			}
			// 忽略404
			else {
				System.out.println( "404" );
				return false;
			}

		}

		// 查询token信息
		Token tokenEntity = SessionManager.getTokenInfo(token);;

		if ( tokenEntity == null ) {
			//throw new JsonException( 403, "登录过期，请重新登录。" );
		}else {
			if ( tokenEntity.getValidTime().getTime() < System.currentTimeMillis() ) {
				//throw new JsonException( 403, "登录过期，请重新登录。" );
			}
		}

		// 设置userId里，后续根据userId，获取用户信息
		request.setAttribute( "userId", "" );

		// 校验权限
		checkPermisson( (HandlerMethod) handler, request );

		return true;

	}

	/**
	 * @作者：王俊辉
	 * 默认需要权限，接口默认权限标识为接口URI， 可用注解
	 * @see .IgnorePermission 忽略权限
	 * @see .Permission 自定义权限标识
	 * 
	 * @param handler
	 * @param request
	 * @throws Exception
	 *             2019年8月8日下午3:57:23
	 */
	private void checkPermisson( HandlerMethod handler, HttpServletRequest request ) throws Exception {
		boolean toValid = true;
		// 接口权限标识
		String thePermission = null;

		// 判断是否是调用Controller，避免拦截静态资源
		if ( handler instanceof HandlerMethod ) {
			IgnorePermission classIgnorePermission = ( (HandlerMethod) handler ).getBeanType()
					.getAnnotation( IgnorePermission.class );
			IgnorePermission methodIgnorePermission = ( (HandlerMethod) handler )
					.getMethodAnnotation( IgnorePermission.class );
			// 如果类上和方法上有 IgnorePermission注解 ，则无需校验权限
			if ( classIgnorePermission != null || methodIgnorePermission != null ) {
				toValid = false;
			}
			Permission classPermission = ( (HandlerMethod) handler ).getBeanType().getAnnotation( Permission.class );
			Permission methodPermission = ( (HandlerMethod) handler ).getMethodAnnotation( Permission.class );
			// 判断类上和方法是否有 Permission注解，该注解自定义接口权限标识，默认标识为URI
			if ( classPermission != null || methodPermission != null ) {
				// 如果方法有 Permission注解，则使用方法自定义接口权限标识
				if ( methodPermission != null ) {
					thePermission = methodPermission.value();
				} else {
					// 如果方法没有而类上有 Permission注解，则使用类上自定义接口权限标识
					if ( classPermission != null ) {
						thePermission = classPermission.value();
					}
				}
			}
		}
		if ( toValid == true ) {
			System.out.println( "需要权限：" + ( thePermission == null ? request.getRequestURI() : thePermission ) );
			if ( request.getRequestURI().equals( thePermission ) ) {
				System.out.println( "权限通-过：" + thePermission );
			}
		} else {
			System.out.println( "无需权限---" + thePermission );
		}
	}
}
