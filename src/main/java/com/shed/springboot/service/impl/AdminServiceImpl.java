package com.shed.springboot.service.impl;

import com.shed.springboot.domain.Member;
import com.shed.springboot.utils.exception.AdminServiceErrorException;
import com.shed.springboot.mapper.AdminMapper;
import com.shed.springboot.domain.Admin;
import com.shed.springboot.service.AdminService;
import com.shed.springboot.utils.exception.MemberServiceErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 管理员业务实现类
 */
@Service
public class AdminServiceImpl implements AdminService {
    private AdminMapper adminMapper;

    @Autowired
    public AdminServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Transactional
    @Override
    public void addAdmin(Member member) throws AdminServiceErrorException {
        // 已成为管理员的用户无法再成为管理员
        if (!adminMapper.selectAllMids().contains(member.getMid())) {
            adminMapper.insertAdmin(member);
        } else {
            throw new AdminServiceErrorException("This member is Already an Administrator!");
        }
    }

    @Transactional
    @Override
    public void removeMemberByMid(Admin admin, Integer mid) throws AdminServiceErrorException {
        // 当前用户无法被删除
        if (!mid.equals(admin.getMid())) {
            adminMapper.deleteMemberByMid(mid);
        } else {
            throw new AdminServiceErrorException("Current Admin CANNOT be Removed!");
        }
    }

    @Transactional
    @Override
    public void removeAdminByAid(Admin admin, Integer aid) throws AdminServiceErrorException {
        // 当前管理员的权限不得被移除
        if (!aid.equals(admin.getAid())) {
            adminMapper.deleteByPrimaryKey(aid);
        } else {
            throw new AdminServiceErrorException("Current Admin CANNOT be Removed!");
        }
    }

    @Override
    public List<Admin> queryAllAdmins() {
        return adminMapper.selectAllAdmins();
    }
}
