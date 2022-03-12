package com.shed.springboot.mapper;

import com.shed.springboot.domain.Admin;
import com.shed.springboot.domain.Member;
import com.shed.springboot.utils.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper extends BaseMapper<Integer,Member> {

    List<Member> selectAllMembers();

    Member selectByMidAndPassword(Integer mid, String password);

    List<Member> selectMembersByPage(Integer currentPage,Integer lineSize);

    List<Integer> selectBidByMid(Integer mid);

    List<Integer> selectAllMid();

    void updatePasswordByMid(String password,Integer mid);

    Admin selectAdminByMid(Integer mid);

    void updateAdminPasswordByMid(String password,Integer mid);
}