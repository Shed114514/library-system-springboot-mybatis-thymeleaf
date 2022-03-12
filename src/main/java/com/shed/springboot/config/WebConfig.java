package com.shed.springboot.config;

import com.shed.springboot.utils.interceptor.AdminInterceptor;
import com.shed.springboot.utils.interceptor.MemberInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 注册视图控制器,实现页面跳转
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/register.html").setViewName("registration");
        registry.addViewController("/error404").setViewName("404");
    }

    // 注册自定义拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/**").excludePathPatterns();
        registry.addInterceptor(new MemberInterceptor()).addPathPatterns("/member/**").excludePathPatterns();
    }
}
