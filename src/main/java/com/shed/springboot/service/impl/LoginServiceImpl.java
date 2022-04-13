package com.shed.springboot.service.impl;

import com.shed.springboot.domain.Admin;
import com.shed.springboot.domain.Member;
import com.shed.springboot.utils.exception.LoginFailedException;
import com.shed.springboot.utils.exception.RegisterFailedException;
import com.shed.springboot.mapper.AdminMapper;
import com.shed.springboot.mapper.MemberMapper;
import com.shed.springboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * 登录与注册业务实现类
 */
@Service
public class LoginServiceImpl implements LoginService {

    private MemberMapper memberMapper;
    private AdminMapper adminMapper;

    @Autowired
    public LoginServiceImpl(MemberMapper memberMapper, AdminMapper adminMapper) {
        this.memberMapper = memberMapper;
        this.adminMapper = adminMapper;
    }

    @Override
    public Object login(Integer mid, String password, Integer role) throws LoginFailedException {
        String passwdDecoded = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        // 当role等于1时为管理员登录,其他为普通用户登录
        if (role == 1) {
            Admin admin = adminMapper.selectByMidAndPassword(mid, passwdDecoded);
            if (admin != null) {
                return admin;
            } else {
                throw new LoginFailedException("This member is NOT an Administrator," +
                                            "or the ID or password you input may be WRONG, please try again!");
            }
        } else {
            Member member = memberMapper.selectByMidAndPassword(mid, passwdDecoded);
            if (member != null) {
                return member;
            } else {
                throw new LoginFailedException("The ID or password you input may be WRONG, please try again!");
            }
        }
    }

    @Transactional
    @Override
    public void register(Member member, String verifyPassword, Integer role) throws RegisterFailedException {
        // 判断所输入的ID是否与数据表中的某个ID一致
        // 由于账户ID是唯一标识,因此注册出现了重复ID报错
        if (memberMapper.selectAllMid().contains(member.getMid())) {
            throw new RegisterFailedException("The ID you input has already existed, please try again!");
        }
        // 判断所输入的ID或密码为空字符
        if ("".equals(member.getMid()) || "".equals(member.getPassword())) {
            throw new RegisterFailedException("ID and Password CANNOT be null, please try again!");
        }
        // 确认所输入的两次用户密码是否一致
        if (!(member.getPassword().equals(verifyPassword))) {
            throw new RegisterFailedException("The two passwords you input do NOT match, please try again!");
        } else {
            // 进行性别的添加,role等于1时为男性,其他为女性
            if (role == 1) {
                member.setGender("Male");
            } else {
                member.setGender("Female");
            }
            // 利用DigestUtils编码工具对用户密码进行不可逆加密
            member.setPassword(DigestUtils.md5DigestAsHex(member.getPassword().getBytes(StandardCharsets.UTF_8)));
            // 将用户实例对象映射到数据库中对应的数据表字段
            memberMapper.insert(member);
        }
    }
}
