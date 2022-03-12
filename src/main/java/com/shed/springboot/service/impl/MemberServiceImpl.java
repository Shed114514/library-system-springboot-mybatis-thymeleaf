package com.shed.springboot.service.impl;

import com.shed.springboot.domain.Admin;
import com.shed.springboot.domain.Book;
import com.shed.springboot.mapper.BookMapper;
import com.shed.springboot.utils.exception.MemberServiceErrorException;
import com.shed.springboot.mapper.MemberMapper;
import com.shed.springboot.domain.Member;
import com.shed.springboot.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 用户业务实现类
 */
@Service
public class MemberServiceImpl implements MemberService {

    private MemberMapper memberMapper;
    private BookMapper bookMapper;
    
    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper,BookMapper bookMapper) {
        this.memberMapper = memberMapper;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<Member> queryMembersByPage(Integer currentPage, Integer lineSize) {
        return memberMapper.selectMembersByPage(currentPage,lineSize);
    }

    @Transactional
    @Override
    public void editProfile(Member member) throws MemberServiceErrorException {
        memberMapper.updateByPrimaryKey(member);
    }

    @Transactional
    @Override
    public void changePassword(Member member, String oldPassword, String newPassword, String confirmPassword) throws MemberServiceErrorException {
        String currentPasswordEncoded = member.getPassword();
        // 利用DigestUtils对已加密的密码进行解密
        String oldPasswdEncoded = DigestUtils.md5DigestAsHex(oldPassword.getBytes(StandardCharsets.UTF_8));
        if (!oldPasswdEncoded.equals(currentPasswordEncoded)) {
            throw new MemberServiceErrorException("The old password does not match with the password of the current member" +
                                                        ", plesae try again!");
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new MemberServiceErrorException("The two passwords you input are inconsistent, plesae try again!");
        } else {
            // 利用DigestUtils编码工具对用户密码进行加密
            member.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes(StandardCharsets.UTF_8)));
            // 将新密码传到数据库对应的表单
            String password = member.getPassword();
            Integer mid = member.getMid();
            memberMapper.updatePasswordByMid(password,mid);
            // 若用户是管理员,则其在管理员表的密码也会相应做出更改
            if (memberMapper.selectAdminByMid(mid) != null) {
                memberMapper.updateAdminPasswordByMid(password,mid);
            }
        }
    }

    @Override
    public List<Member> queryAllMembers() {
        return memberMapper.selectAllMembers();
    }

    @Override
    public List<Member> queryAllBorrowingInfo() {
        List<Member> memberList = memberMapper.selectAllMembers();
        for (Member member : memberList) {
            List<Book> books = bookMapper.selectBookByMid(member.getMid());
            member.setBookList(books);
        }
        return memberList;
    }

    @Override
    public Member queryCurrentBorrowingInfo(Integer mid) {
        Member member = memberMapper.selectByPrimaryKey(mid);
        List<Book> books = bookMapper.selectBookByMid(mid);
        member.setBookList(books);
        return member;
    }
}
