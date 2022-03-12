package com.shed.springboot.utils.interceptor;

import com.shed.springboot.domain.Admin;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 * 当以管理员登录时,获取域中Admin实例的session对象
 * 若域中没有相应的session对象则直接跳转到登录页面,通过登录账户获取权限
 */
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin == null) {
            response.sendRedirect(request.getContextPath() + "/login.html");
            return false;
        }
        return true;
    }
}
