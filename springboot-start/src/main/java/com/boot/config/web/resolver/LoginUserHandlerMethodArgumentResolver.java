package com.boot.config.web.resolver;


import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * Controller有@StaffUser注解的方法参数，注入当前登录用户.StaffInfo
 * @author huangxianghan
 */
//@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return false;
        //return parameter.getParameterType().isAssignableFrom(StaffInfo.class) && parameter.hasParameterAnnotation(StaffUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {

        //StaffInfo userInfo = SessionManager.getStaffInfo();

        return null;
    }
}
