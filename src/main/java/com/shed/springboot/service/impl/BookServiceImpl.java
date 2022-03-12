package com.shed.springboot.service.impl;

import com.shed.springboot.domain.Admin;
import com.shed.springboot.domain.Member;
import com.shed.springboot.mapper.MemberMapper;
import com.shed.springboot.utils.exception.BookServiceErrorException;
import com.shed.springboot.mapper.BookMapper;
import com.shed.springboot.domain.Book;
import com.shed.springboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 图书业务实现类
 */
@Service
public class BookServiceImpl implements BookService {

    private MemberMapper memberMapper;
    private BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(MemberMapper memberMapper,BookMapper bookMapper) {
        this.memberMapper = memberMapper;
        this.bookMapper = bookMapper;
    }

    @Transactional
    @Override
    public void addBook(Book book) throws BookServiceErrorException {
        if (book.getTitle() == null || book.getPubdate() == null) {
            throw new BookServiceErrorException("Title and publication date CANNOT be null, please try again!");
        }
        if (bookMapper.selectByPrimaryKey(book.getBid()) != null) {
            throw new BookServiceErrorException("Book ID has existed, please try again!");
        } else {
            bookMapper.insert(book);
        }
    }

    @Transactional
    @Override
    public void removeBookByBid(Integer bid) throws BookServiceErrorException {
        // 若图书已借出,无法删除,因此需要判断图书是否已借出
        if (bookMapper.selectMidByBid(bid) == null) {
            bookMapper.deleteByPrimaryKey(bid);
        } else {
            throw new BookServiceErrorException("Book CANNOT be Removed when it's Borrowed!");
        }
    }

    @Transactional
    @Override
    public void editBook(Book book) throws BookServiceErrorException {
        try {
            bookMapper.updateByPrimaryKey(book);
        } catch (Exception e) {
            throw new BookServiceErrorException("The Book ID CANNOT be Modified!");
        }
    }

    @Override
    public Book queryBookById(Integer bid) {
        return bookMapper.selectByPrimaryKey(bid);
    }

    @Override
    public List<Book> queryAllBooks() {
        return bookMapper.selectAllBooks();
    }

    @Transactional
    @Override
    public void borrowBook(Integer mid, Integer bid) throws BookServiceErrorException {
        // 图书数量为0时,无法再借阅图书
        if (bookMapper.selectNumberByPrimaryKey(bid) == 0) {
            throw new BookServiceErrorException("The book you borrow is under stock!");
        }
        // 同一本书每个用户只能借一次且图书数量不等于0,可借阅图书
        if (memberMapper.selectBidByMid(mid).contains(bid)) {
            throw new BookServiceErrorException("For the same book, each member can only borrow it once!");
        } else {
            // 建立用户与图书的关联
            bookMapper.insertMidAndBid(mid,bid);
            // 图书库借出一本书,其相应的数量减一
            bookMapper.updateNumberDecrementByBid(bid);
        }
    }

    @Transactional
    @Override
    public void returnBook(HttpSession session, String objectName, Integer mid, Integer bid) throws BookServiceErrorException {
        Object object = session.getAttribute(objectName);
        if (object instanceof Member) {
            object = (Member) object;
            if (!mid.equals(((Member) object).getMid())) {
                throw new BookServiceErrorException("Please check the borrower ID if it's matched with the current member ID!");
            }
        } else if (object instanceof Admin) {
            object = (Admin) object;
            if (!mid.equals(((Admin) object).getMid())) {
                throw new BookServiceErrorException("Please check the borrower ID if it's matched with the current member ID!");
            }
        }
        // 用户归还图书
        // 先删除用户与图书的关联
        bookMapper.deleteRelationByBid(bid);
        // 用户归还图书时,所对应图书的数量加一
        bookMapper.updateNumberIncrementByBid(bid);
    }
}
