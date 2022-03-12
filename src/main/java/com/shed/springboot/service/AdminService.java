package com.shed.springboot.service;

import com.shed.springboot.domain.Admin;
import com.shed.springboot.domain.Member;
import com.shed.springboot.utils.exception.AdminServiceErrorException;

import java.util.List;

/**
 * 管理员业务接口
 */
public interface AdminService {

    /**
     * 添加管理员
     * @param member 从前端接受数据封装成Member实例
     */
    void addAdmin(Member member) throws AdminServiceErrorException;

    /**
     * 移除管理员,当前管理员不能被移除
     * @param admin 管理员Admin实例
     * @param aid 管理员ID
     */
    void removeAdminByAid(Admin admin, Integer aid) throws AdminServiceErrorException;

    /**
     * 查询所有管理员
     * @return 返回管理员List集合
     */
    List<Admin> queryAllAdmins();

    /**
     * 删除用户,当前Session对象不能被删除
     * @param admin 管理员Admin对象
     * @param mid 用户ID
     */
    void removeMemberByMid(Admin admin, Integer mid) throws AdminServiceErrorException;
}
