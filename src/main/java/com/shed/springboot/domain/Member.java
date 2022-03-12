package com.shed.springboot.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 图书系统普通用户
 */
public class Member implements Serializable {
    private Integer mid;

    private String password;

    private String name;

    private String gender;

    private Date birthday;

    // 用户和图书形成多对多的关系,需要一个中间表建立两者关系
    // 一个用户可借阅多本不同的书
    private List<Book> bookList;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "Member{" +
                "mid=" + mid +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", bookList=" + bookList +
                '}';
    }
}