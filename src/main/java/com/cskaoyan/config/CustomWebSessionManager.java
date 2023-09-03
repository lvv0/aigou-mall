package com.cskaoyan.config;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author ：lww
 * @description：sessionManager
 * @date ：2021/8/13 13:46
 */
public class CustomWebSessionManager extends DefaultWebSessionManager {
    @Override
    protected Serializable getSessionId(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String sessionId;
        sessionId = request.getHeader("X-cskaoyan-mall-Admin-Token");
        if (sessionId != null && !"".equals(sessionId)){
            return sessionId;
        }
        sessionId = request.getHeader("X-Litemall-Token");
        if (sessionId != null && !"".equals(sessionId)){
            return sessionId;
        }
            return super.getSessionId(request, servletResponse);
    }
}
