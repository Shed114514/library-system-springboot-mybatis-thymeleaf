package com.shed.springboot.service;

import com.shed.springboot.domain.Admin;
import com.shed.springboot.domain.Book;
import com.shed.springboot.utils.exception.BookServiceErrorException;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 图书业务接口
 */
public interface BookService {

    /**
     * @param book 接收前端参数并自动封装成图书实例
     */
    void addBook(Book book) throws BookServiceErrorException;

    /**
     * 删除图书
     * @param bid 图书ID
     */
    void removeBookByBid(Integer bid) throws BookServiceErrorException;

    /**
     * 修改图书信息
     * @param book 接收前端参数并自动封装成图书实例
     */
    void editBook(Book book) throws BookServiceErrorException ;

    /**
     * 借阅图书
     * @param mid 用户ID
     * @param bid 图书ID
     */
    void borrowBook(Integer mid, Integer bid) throws BookServiceErrorException;

    /**
     * 图书归还
     * @param session 从域中获取session对象
     * @param objectName 对象名称
     * @param mid 用户ID
     * @param bid 图书ID
     */
    void returnBook(HttpSession session, String objectName, Integer mid, Integer bid) throws BookServiceErrorException;

    /**
     * 根据ID查询图书
     */
    Book queryBookById(Integer id);

    /**
     * 查询所有图书
     * @return 返回图书List集合
     */
    List<Book> queryAllBooks();
}
