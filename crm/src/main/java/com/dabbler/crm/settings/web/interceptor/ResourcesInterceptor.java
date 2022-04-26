package com.dabbler.crm.settings.web.interceptor;

import com.dabbler.crm.commons.contants.Contants;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ResourcesInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute(Contants.SESSION_USER) == null) {
            response.sendRedirect(request.getContextPath());//手动重定向时要写项目名字,注意不要写死
            return false;
        }
        return true;
    }
}
