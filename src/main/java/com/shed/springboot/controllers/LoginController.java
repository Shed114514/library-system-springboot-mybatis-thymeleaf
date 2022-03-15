package com.shed.springboot.controllers;

import com.shed.springboot.domain.Admin;
import com.shed.springboot.domain.Member;
import com.shed.springboot.utils.exception.LoginFailedException;
import com.shed.springboot.utils.exception.RegisterFailedException;
import com.shed.springboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 图书系统用户或管理员登录以及用户注册控制层
 */
@Controller
public class LoginController {
    /**
     * 登录与注册业务接口
     */
    private LoginService loginService;

    /**
     * 通过构造方法注入业务接口
     * @param loginService 登录与注册业务接口
     */
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 用户登录,进行用户账户和密码的验证
     * @param session 当前会话对象
     * @param role 当role等于1时为管理员登录,而等于其他值则普通用户登录
     * @param mid 用户账号
     * @param password 用户密码
     * @return 登录成功跳转到管理员或者普通用户主页,失败则跳转回登录页面并显示登陆失败的提示
     */
    @RequestMapping("/login")
    public String login(HttpSession session,
                        Integer role,
                        Integer mid,
                        String password)
            throws LoginFailedException {
        Object object = loginService.login(mid,password,role);
        if (object instanceof Admin) {
            Admin admin = (Admin) object;
            session.setAttribute("admin",admin);
            return "redirect:/admin/home";
        } else if (object instanceof Member) {
            Member member = (Member) object;
            session.setAttribute("member",member);
            return "redirect:/member/home";
        }
        return null ;
    }

    /**
     * 用户注册
     * @param attributes 路径重定向时使用RedirectAttributes传递参数(重定向时Model无法传递参数),进行相关提示操作
     * @param member 接收前端参数并自动封装成用户Member实例
     * @param verifyPassword 验证密码,确认输入的两次用户密码是否一致
     * @param role 当role等于1时为男性,而等于其他值则为女性
     * @return 注册成功后跳转到登录页面并显示注册成功的提示,失败则出现注册失败的提示
     */
    @RequestMapping("/register")
    public String register(RedirectAttributes attributes,
                           Member member,
                           String verifyPassword,
                           Integer role)
            throws RegisterFailedException {
        loginService.register(member,verifyPassword,role);
        attributes.addFlashAttribute("success","Registration Successful!");
        return "redirect:/login.html";
    }

    /**
     * 当前用户登出,返回登录界面
     * @param session 获取域中Session对象
     * @param attributes 路径重定向时使用RedirectAttributes传递参数(重定向时Model无法传递参数)
     * @return 移除当前session后跳转回登录页面并出现登出成功的提示
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session,RedirectAttributes attributes) {
        session.invalidate();
        attributes.addFlashAttribute("logout","Logged Out Successfully!");
        return "redirect:/login.html";
    }
}
