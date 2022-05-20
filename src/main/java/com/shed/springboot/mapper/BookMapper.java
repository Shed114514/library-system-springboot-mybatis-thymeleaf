package com.shed.springboot.mapper;

import com.shed.springboot.domain.Book;
import com.shed.springboot.domain.Member;
import com.shed.springboot.utils.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Integer,Book> {

    List<Book> selectAllBooks();

    int selectNumberByPrimaryKey(Integer bid);

    void updateNumberDecrementByBid(Integer bid);

    void updateNumberIncrementByBid(Integer bid);

    Member selectMidByBid(Integer bid);

    void insertMidAndBid(Integer mid, Integer bid);

    List<Book> selectBookByMid(Integer mid);

    void deleteRelationByBidAndMid(Integer bid,Integer mid);
}