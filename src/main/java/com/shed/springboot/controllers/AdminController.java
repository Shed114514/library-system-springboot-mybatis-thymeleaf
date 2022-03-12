package com.shed.springboot.controllers;

import com.shed.springboot.domain.Admin;
import com.shed.springboot.domain.Book;
import com.shed.springboot.domain.Member;
import com.shed.springboot.utils.exception.AdminServiceErrorException;
import com.shed.springboot.utils.exception.BookServiceErrorException;
import com.shed.springboot.utils.exception.MemberServiceErrorException;
import com.shed.springboot.service.AdminService;
import com.shed.springboot.service.BookService;
import com.shed.springboot.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 图书系统管理员控制层
 */
@Controller
@RequestMapping("/admin")
@SessionAttributes("admin")
public class AdminController {
    /**
     * 业务接口
     */
    private MemberService memberService;
    private AdminService adminService;
    private BookService bookService;

    /**
     * 通过构造方法注入业务接口
     * @param memberService 用户业务接口
     * @param adminService 管理员业务接口
     * @param bookService 图书业务接口
     */
    @Autowired
    public AdminController(MemberService memberService,
                           AdminService adminService,
                           BookService bookService) {
        this.memberService = memberService;
        this.adminService = adminService;
        this.bookService = bookService;
    }

    /**
     * 登入管理员页面
     * @param admin 从域中获取Admin的session对象
     * @param model 通过model向前端页面传入参数
     * @return 跳转到管理员首页
     */
    @GetMapping("/home")
    public String adminHome(@SessionAttribute("admin") Admin admin, Model model) {
        model.addAttribute("admin",admin);
        return "admin/admin_home";
    }

    /**
     * 管理员信息页面
     * @param admin 从域中获取Admin的session对象
     * @param model 通过model向前端页面传入参数
     * @return 跳转到管理员Admin信息页面
     */
    @GetMapping("/profile")
    public String adminProfile(@SessionAttribute("admin") Admin admin, Model model) {
        model.addAttribute("admin",admin);
        return "admin/admin_profile";
    }

    /**
     * 图书添加页面
     * @param admin 从域中获取Admin的session对象
     * @param model 通过model向前端页面传入参数
     * @return 跳转到图书添加页面
     */
    @GetMapping("/book/add.html")
    public String addBookPage(@SessionAttribute("admin") Admin admin, Model model) {
        model.addAttribute("admin",admin);
        return "admin/book_add";
    }

    /**
     * 添加图书
     * @param attributes 路径重定向时使用RedirectAttributes传递参数(重定向时Model无法传递参数)
     * @param book 接收前端参数并自动封装成图书实例
     * @return 添加成功后跳转到添加页面并出现添加成功的提示
     */
    @RequestMapping("/book/add")
    public String addBook(RedirectAttributes attributes, Book book) throws BookServiceErrorException {
        bookService.addBook(book);
        attributes.addFlashAttribute("success","New Book Added Successfully!");
        return "redirect:/admin/book/add.html" ;
    }

    /**
     * 查询全部图书
     * @param admin 从域中获取Admin的session对象
     * @param model 通过model向前端页面传入参数
     * @return 在前端页面遍历集合显示图书列表
     */
    @GetMapping("/book/queryAll")
    public String queryAllBooks(@SessionAttribute("admin") Admin admin,Model model) {
        List<Book> bookList = bookService.queryAllBooks();
        model.addAttribute("admin",admin);
        model.addAttribute("bookList",bookList);
        return "admin/book_table";
    }

    /**
     * 图书借阅信息页面
     * @param admin 从域中获取Admin的session对象
     * @param model 通过model向前端页面传入参数
     * @return 列出所有的已借出的图书ID和书名,以及借阅人ID和姓名
     */
    @GetMapping("/book/borrow.html")
    public String bookBorrowPage(@SessionAttribute("admin") Admin admin,Model model) {
        List<Member> memberList = memberService.queryAllBorrowingInfo();
        model.addAttribute("admin",admin);
        model.addAttribute("memberList",memberList);
        return "admin/book_borrow_table";
    }

    /**
     * 借阅图书
     * @param attributes 路径重定向时使用RedirectAttributes传递参数(重定向时Model无法传递参数)
     * @param bid 图书ID
     * @return 借阅成功则返回图书列表并出现借阅成功的提示,失败则出现借阅失败的提示
     */
    @RequestMapping("/book/borrow")
    public String borrowBook(@SessionAttribute("admin") Admin admin,
                             RedirectAttributes attributes,
                             Integer bid) throws BookServiceErrorException {
        bookService.borrowBook(admin.getMid(),bid);
        attributes.addFlashAttribute("success","Book Borrowed Successfully!") ;
        return "redirect:/admin/book/queryAll";
    }

    /**
     * 图书归还
     * @param session 从域中获取session对象
     * @param objectName 当前操作对象的名称
     * @param mid 接收前端传递的用户ID
     * @param bid 接收前端传递的图书ID
     * @param attributes 路径重定向时使用RedirectAttributes传递参数(重定向时Model无法传递参数)
     * @return 归还成功则返回图书列表并出现归还成功的提示,失败则出现借阅失败的提示
     */
    @RequestMapping("/book/return")
    public String returnBook(HttpSession session,
                             String objectName,
                             Integer mid,
                             Integer bid,
                             RedirectAttributes attributes) throws BookServiceErrorException {
        bookService.returnBook(session,"admin",mid,bid);
        attributes.addFlashAttribute("success","Book Returned Successfully!") ;
        return "redirect:/admin/book/borrow.html" ;
    }

    /**
     * 图书信息编辑页面
     * @param admin 从域中获取Admin的session对象
     * @param model 通过model向前端页面传入参数
     * @param id 根据前端传递的id查找所对应的图书
     * @return 跳转到图片编辑页面
     */
    @GetMapping("/book/edit.html")
    public String editBookPage(@SessionAttribute("admin") Admin admin, Model model, Integer id) {
        Book book = bookService.queryBookById(id);
        model.addAttribute("admin",admin);
        model.addAttribute("book",book);
        return "admin/book_edit" ;
    }
    /**
     * 修改特定图书的信息
     * @param attributes 路径重定向时使用RedirectAttributes传递参数(重定向时Model无法传递参数)
     * @param book 接收前端参数并自动封装成图书实例
     * @return 图书编辑成功则跳转到图书列表并返回编辑成功的提示,失败则出现编辑失败的提示
     */
    @RequestMapping("/book/edit")
    public String editBook(RedirectAttributes attributes, Book book) throws BookServiceErrorException {
        bookService.editBook(book);
        attributes.addFlashAttribute("success","Book Info Edited Successfully!") ;
        return "redirect:/admin/book/queryAll";
    }

    /**
     * 删除图书
     * @param bid 从前端接收到的图书ID
     * @param attributes 路径重定向时使用RedirectAttributes传递参数(重定向时Model无法传递参数)
     * @return 图书删除成功则跳转到图书列表并返回删除成功的提示,失败则抛出异常并捕获,然后在前端显示相应的错误提示
     */
    @RequestMapping("/book/remove/{bid}")
    public String removeBook(@PathVariable("bid") Integer bid,
                             RedirectAttributes attributes) throws BookServiceErrorException {
        bookService.removeBookByBid(bid);
        attributes.addFlashAttribute("success","Book Removed Successfully!") ;
        return "redirect:/admin/book/queryAll";
    }

    /**
     * 查询全部用户
     * @param admin 从域中获取Admin的session对象
     * @param model 通过model向前端页面传入参数
     * @return 在前端页面遍历集合显示用户列表
     */
    @GetMapping("/member/queryAll")
    public String queryAllMembers(@SessionAttribute("admin") Admin admin, Model model) {
        System.out.println("query: " + admin);
        List<Member> memberList = memberService.queryAllMembers();
        model.addAttribute("admin",admin);
        model.addAttribute("memberList",memberList);
        return "admin/member_table";
    }

    /**
     * 删除用户,当前Session对象不能被删除
     * @param admin 从域中获取Admin的session对象
     * @param mid 从前端接收到的用户ID
     * @param attributes 路径重定向时使用RedirectAttributes传递参数(重定向时Model无法传递参数)
     * @return 用户删除成功则跳转到用户列表并返回删除成功的提示,失败则抛出异常并捕获,然后在前端显示相应的错误提示
     */
    @RequestMapping("/member/remove/{mid}")
    public String removeMember(@SessionAttribute("admin") Admin admin,
                               @PathVariable("mid") Integer mid,
                               RedirectAttributes attributes) throws MemberServiceErrorException, AdminServiceErrorException {
        adminService.removeMemberByMid(admin,mid);
        attributes.addFlashAttribute("success","Member Removed Successfully!") ;
        return "redirect:/admin/member/queryAll";
    }

    /**
     * 添加管理员
     * @param admin 从前端接受数据封装成管理员Admin实例
     * @param attributes 路径重定向时使用RedirectAttributes传递参数(重定向时Model无法传递参数)
     * @return 管理员添加成功则跳转到用户列表并返回删除成功的提示,失败则抛出异常并捕获,然后在前端显示相应的错误提示
     */
    @RequestMapping("/member/addAdmin")
    public String addAdmin(Member member, RedirectAttributes attributes) throws AdminServiceErrorException {
        adminService.addAdmin(member);
        attributes.addFlashAttribute("success",member.getName() + " has become an Administrator!") ;
        return "redirect:/admin/member/queryAll";
    }

    /**`
     * 所有管理员列表
     * @param admin 从域中获取Admin的session对象
     * @param model 通过model向前端页面传入参数
     * @return 在前端页面遍历集合显示管理员列表
     */
    @GetMapping("/queryAll")
    public String queryAllAdmins(@SessionAttribute("admin") Admin admin, Model model) {
        List<Admin> adminList = adminService.queryAllAdmins();
        model.addAttribute("admin",admin);
        model.addAttribute("adminList",adminList);
        return "admin/admin_table";
    }

    /**
     * 移除管理员,当前管理员不能被移除
     * @param admin 从域中获取Admin的session对象
     * @param aid 从前端接受到的管理员ID
     * @param attributes 路径重定向时使用RedirectAttributes传递参数(重定向时Model无法传递参数)
     * @return 管理员删除成功则跳转到管理员列表并返回删除成功的提示,失败则抛出异常并捕获,然后在前端显示相应的错误提示
     */
    @RequestMapping("/remove/{aid}")
    public String removeAdmin(@SessionAttribute("admin") Admin admin,
                              @PathVariable("aid") Integer aid,
                              RedirectAttributes attributes) throws AdminServiceErrorException {
        adminService.removeAdminByAid(admin,aid);
        attributes.addFlashAttribute("success", "Admin Removed Successfully!");
        return "redirect:/admin/queryAll";
    }
}
