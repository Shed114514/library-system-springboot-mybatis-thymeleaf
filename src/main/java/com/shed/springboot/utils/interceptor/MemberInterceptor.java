package com.shed.springboot.utils.interceptor;

import com.shed.springboot.domain.Member;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 * 当以普通用户登录时,获取域中Member实例的session对象
 * 若域中没有相应的session对象则直接跳转到登录页面,通过登录账户获取权限
 */
public class MemberInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Member member = (Member) request.getSession().getAttribute("member");
        if (member == null) {
            response.sendRedirect(request.getContextPath() + "/login.html");
            return false;
        }
        return true;
    }
}
