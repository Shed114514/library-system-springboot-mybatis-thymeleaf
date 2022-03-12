package com.shed.springboot.service;

import com.shed.springboot.domain.Member;
import com.shed.springboot.utils.exception.LoginFailedException;
import com.shed.springboot.utils.exception.RegisterFailedException;

/**
 * 登录与注册业务接口
 */
public interface LoginService {

    /**
     * 用户登录,进行用户账户和密码的验证
     * @param mid 用户账号
     * @param password 用户密码
     * @param role 当role等于1时为管理员登录,而等于其他值则普通用户登录
     * @return 返回管理员对象admin或者普通用户对象member,若有异常则抛出
     */
    Object login(Integer mid, String password, Integer role) throws LoginFailedException;

    /**
     * 用户注册
     * @param member 前端接收参数并自动封装成用户实例
     * @param verifyPassword 验证密码,确认输入的两次用户密码是否一致
     * @param role 当role等于1时为男性,而等于其他值则为女性
     */
    void register(Member member, String verifyPassword, Integer role) throws RegisterFailedException;
}
