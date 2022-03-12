package com.shed.springboot.domain;

import java.io.Serializable;

/**
 * 图书系统管理员
 */
public class Admin implements Serializable {
    private Integer aid;

    private String password;

    private String name;

    private Integer mid;

    // 管理员与用户形成一对一的关系
    private Member member;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "aid=" + aid +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}