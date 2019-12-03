package com.wjh.shiro.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.subject.WebSubjectContext;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义：SubjectFactory
 * @description:
 * @author: Mr.Wang
 * @create: 2019-10-12 13:37
 **/
public class SysDefaultWebSubjectFactory extends DefaultWebSubjectFactory {

    public SysDefaultWebSubjectFactory() {
        super();
    }

    public Subject createSubject(SubjectContext context) {
        if (!(context instanceof WebSubjectContext)) {
            return super.createSubject(context);
        }
        WebSubjectContext wsc = (WebSubjectContext) context;
        SecurityManager securityManager = wsc.resolveSecurityManager();
        Session session = wsc.resolveSession();
        boolean sessionEnabled = wsc.isSessionCreationEnabled();
        PrincipalCollection principals = wsc.resolvePrincipals();
        boolean authenticated = wsc.resolveAuthenticated();
        String host = wsc.resolveHost();
        ServletRequest request = wsc.resolveServletRequest();
        ServletResponse response = wsc.resolveServletResponse();

        return new SysWebDelegatingSubject(principals, authenticated, host, session, sessionEnabled,
                request, response, securityManager);
    }

    /**
     * @deprecated since 1.2 - override {@link #createSubject(org.apache.shiro.subject.SubjectContext)} directly if you
     *             need to instantiate a custom {@link Subject} class.
     */
    @Deprecated
    protected Subject newSubjectInstance(PrincipalCollection principals, boolean authenticated,
                                         String host, Session session,
                                         ServletRequest request, ServletResponse response,
                                         SecurityManager securityManager) {
        return new SysWebDelegatingSubject(principals, authenticated, host, session, true,
                request, response, securityManager);
    }



}
