package com.shed.springboot.controllers;

import com.shed.springboot.domain.Member;
import com.shed.springboot.domain.Book;
import com.shed.springboot.domain.Member;
import com.shed.springboot.service.MemberService;
import com.shed.springboot.service.BookService;
import com.shed.springboot.service.MemberService;
import com.shed.springboot.utils.exception.BookServiceErrorException;
import com.shed.springboot.utils.exception.MemberServiceErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;

/**
 * 图书管理系统普通用户控制层
 */
@Controller
@RequestMapping("/member")
@SessionAttributes("member")
public class MemberController {
    /**
     * 业务接口
     */
    private MemberService memberService;
    private BookService bookService;

    /**
     * 通过构造方法注入业务接口
     * @param memberService 用户业务接口
     * @param bookService 图书业务接口
     */
    @Autowired
    public MemberController(MemberService memberService,
                           BookService bookService) {
        this.memberService = memberService;
        this.bookService = bookService;
    }

    /**
     * 登入用户页面
     * @param member 从域中获取当前session对象
     * @param model 通过model向前端页面传入参数
     * @return 跳转到用户首页
     */
    @GetMapping("/home")
    public String memberHome(@SessionAttribute("member") Member member, Model model) {
        model.addAttribute("member",member);
        return "member/member_home";
    }

    /**
     * 用户信息页面
     * @param member 从域中获取当前session对象
     * @param model 通过model向前端页面传入参数
     * @return 跳转到用户member信息页面
     */
    @GetMapping("/profile")
    public String memberProfile(@SessionAttribute("member") Member member, Model model) {
        model.addAttribute("member",member);
        return "member/member_profile";
    }

    /**
     * 修改当前用户信息
     * @param member 从前端接收到的数据封装成Member对象
     * @param attributes 路径重定向时使用RedirectAttributes传递参数(重定向时Model无法传递参数)
     */
    @RequestMapping("/profile/edit")
    public String editProfile(Member member,RedirectAttributes attributes) throws MemberServiceErrorException {
        memberService.editProfile(member);
        attributes.addFlashAttribute("success","Your profile edited successfully");
        return "redirect:/member/profile" ;
    }

    /**
     * 修改用户密码页面
     * @param member 从域中获取当前session对象
     * @param model 通过model向前端页面传入参数
     * @return 跳转到密码修改页面
     */
    @RequestMapping("/security")
    public String securityPage(@SessionAttribute("member") Member member, Model model) {
        model.addAttribute("member",member);
        return "member/member_security";
    }

    /**
     * 修改当前用户密码
     * @param member 从前端接收到的数据封装成Member对象
     * @param newPassword 新密码
     * @param confirmPassword 确认新密码是否一致
     * @param session 从域中获取session对象
     * @param attributes 路径重定向时使用RedirectAttributes传递参数(重定向时Model无法传递参数)
     * @return 密码修改成功后跳转到登录页面,需要重新登录以验证新密码
     */
    @RequestMapping("/security/password")
    public String changePassword(@SessionAttribute("member") Member member,
                                 String oldPassword,
                                 String newPassword,
                                 String confirmPassword,
                                 HttpSession session,
                                 RedirectAttributes attributes) throws MemberServiceErrorException {
        memberService.changePassword(member,oldPassword,newPassword,confirmPassword);
        session.invalidate();
        attributes.addFlashAttribute("success","Your password has been changed successfully" +
                                                    ", please re-log in for verification!");
        return "redirect:/login.html";
    }


    /**
     * 查询全部图书
     * @param member 从域中获取当前session对象
     * @param model 通过model向前端页面传入参数
     * @return 在前端页面遍历集合显示图书列表
     */
    @GetMapping("/book/queryAll")
    public String queryAllBooks(@SessionAttribute("member") Member member,Model model) {
        List<Book> bookList = bookService.queryAllBooks();
        model.addAttribute("member",member);
        model.addAttribute("bookList",bookList);
        return "member/book_table";
    }

    /**
     * 图书借阅信息页面
     * @param member 从域中获取当前session对象
     * @param model 通过model向前端页面传入参数
     * @return 列出所有的已借出的图书ID和书名,以及借阅人ID和姓名
     */
    @GetMapping("/book/borrow.html")
    public String bookBorrowPage(@SessionAttribute("member") Member member,Model model) {
        Member currentMember = memberService.queryCurrentBorrowingInfo(member.getMid());
        model.addAttribute("member",currentMember);
        return "member/book_borrow_table";
    }

    /**
     * 借阅图书
     * @param member 从域中获取当前session对象
     * @param attributes 路径重定向时使用RedirectAttributes传递参数(重定向时Model无法传递参数)
     * @param bid 图书ID
     * @return 借阅成功则返回图书列表并出现借阅成功的提示,失败则出现借阅失败的提示
     */
    @RequestMapping("/book/borrow")
    public String borrowBook(@SessionAttribute("member") Member member,
                             RedirectAttributes attributes,
                             Integer bid) throws BookServiceErrorException {
        bookService.borrowBook(member.getMid(),bid);
        attributes.addFlashAttribute("success","Book Borrowed Successfully!") ;
        return "redirect:/member/book/queryAll";
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
        bookService.returnBook(session,"member",mid,bid);
        attributes.addFlashAttribute("success","Book Returned Successfully!") ;
        return "redirect:/member/book/borrow.html" ;
    }
}
