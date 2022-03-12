package com.shed.springboot.mapper;

import com.shed.springboot.domain.Admin;
import com.shed.springboot.domain.Member;
import com.shed.springboot.utils.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper extends BaseMapper<Integer,Admin> {

    Admin selectByMidAndPassword(Integer mid, String password);

    List<Admin> selectAllAdmins();

    List<Integer> selectAllMids();

    void insertAdmin(Member member);

    void deleteMemberByMid(Integer mid);
}