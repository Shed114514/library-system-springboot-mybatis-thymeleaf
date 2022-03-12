package com.shed.springboot.service;

import com.shed.springboot.domain.Admin;
import com.shed.springboot.domain.Member;
import com.shed.springboot.utils.exception.MemberServiceErrorException;

import java.util.List;

/**
 * 用户业务接口
 */
public interface MemberService {

    /**
     * 实现分页查询用户列表
     */
    List<Member> queryMembersByPage(Integer currentPage, Integer lineSize);

    /**
     * 查询所有用户
     * @return 返回用户List集合
     */
    List<Member> queryAllMembers();

    /**
     * 查询所有用户借阅信息
     */
    List<Member> queryAllBorrowingInfo();

    /**
     * 查询当前用户借阅信息
     */
    Member queryCurrentBorrowingInfo(Integer mid);

    /**
     * 修改用户信息
     * @param member 接收前端参数并自动封装成用户实例
     */
    void editProfile(Member member) throws MemberServiceErrorException;

    /**
     * 修改当前用户密码
     * @param member 当前member实例对象
     * @param newPassword 新密码
     * @param confirmPassword 确认新密码是否一致
     */
    void changePassword(Member member,String oldPassword, String newPassword, String confirmPassword) throws MemberServiceErrorException;
}
