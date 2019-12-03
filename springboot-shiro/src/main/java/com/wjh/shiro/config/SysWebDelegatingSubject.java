package com.wjh.shiro.config;

import com.wjh.shiro.config.filter.RequestFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DelegatingSubject;
import org.apache.shiro.subject.support.DisabledSessionException;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * 自定义：Subject
 * 过滤无用token，只能在登录接口才能创建和保存session
 * @author: Mr.Wang
 * @create: 2019-10-12 13:39
 **/
public class SysWebDelegatingSubject extends WebDelegatingSubject {

    private static final Logger log = LoggerFactory.getLogger(DelegatingSubject.class);

    public SysWebDelegatingSubject(PrincipalCollection principals, boolean authenticated, String host, Session session, ServletRequest request, ServletResponse response, SecurityManager securityManager) {
        super(principals, authenticated, host, session, request, response, securityManager);
    }

    public SysWebDelegatingSubject(PrincipalCollection principals, boolean authenticated, String host, Session session, boolean sessionEnabled, ServletRequest request, ServletResponse response, SecurityManager securityManager) {
        super(principals, authenticated, host, session, sessionEnabled, request, response, securityManager);
    }

    @Override
    public Session getSession(boolean create) {

        if (log.isTraceEnabled()) {
            log.trace("attempting to get session; create = " + create +
                    "; session is null = " + (this.session == null) +
                    "; session has id = " + (this.session != null && session.getId() != null));
        }

        if (this.session == null && create) {

            HttpServletRequest httpServletRequest = RequestFilter.request.get();
            if (!httpServletRequest.getRequestURI().equals("/login")){
                return new Session() {
                    @Override
                    public Serializable getId() {
                        return null;
                    }

                    @Override
                    public Date getStartTimestamp() {
                        return null;
                    }

                    @Override
                    public Date getLastAccessTime() {
                        return null;
                    }

                    @Override
                    public long getTimeout() throws InvalidSessionException {
                        return 0;
                    }

                    @Override
                    public void setTimeout(long maxIdleTimeInMillis) throws InvalidSessionException {

                    }

                    @Override
                    public String getHost() {
                        return null;
                    }

                    @Override
                    public void touch() throws InvalidSessionException {

                    }

                    @Override
                    public void stop() throws InvalidSessionException {

                    }

                    @Override
                    public Collection<Object> getAttributeKeys() throws InvalidSessionException {
                        return null;
                    }

                    @Override
                    public Object getAttribute(Object key) throws InvalidSessionException {
                        return null;
                    }

                    @Override
                    public void setAttribute(Object key, Object value) throws InvalidSessionException {

                    }

                    @Override
                    public Object removeAttribute(Object key) throws InvalidSessionException {
                        return null;
                    }
                };
            }

            //added in 1.2:
            if (!isSessionCreationEnabled()) {
                String msg = "Session creation has been disabled for the current subject.  This exception indicates " +
                        "that there is either a programming error (using a session when it should never be " +
                        "used) or that Shiro's configuration needs to be adjusted to allow Sessions to be created " +
                        "for the current Subject.  See the " + DisabledSessionException.class.getName() + " JavaDoc " +
                        "for more.";
                throw new DisabledSessionException(msg);
            }

            log.trace("Starting session for host {}", getHost());
            SessionContext sessionContext = createSessionContext();
            Session session = this.securityManager.start(sessionContext);
            this.session = decorate(session);
        }
        return this.session;
    }
}

