package com.shed.springboot.utils.base;

/**
 * 定义一个公共的Mapper接口,提供Mapper的公共接口
 */
public interface BaseMapper<PK,VO> {

    /**
     * 实现数据增加
     */
    int insert(VO vo);

    /**
     * 实现数据选择性增加
     */
    int insertSelective(VO vo);

    /**
     * 根据主键实现数据删除
     */
    void deleteByPrimaryKey(PK pk);

    /**
     * 根据主键实现数据选择性修改
     */
    int updateByPrimaryKeySelective(VO vo);

    /**
     * 根据主键实现数据修改
     */
    void updateByPrimaryKey(VO vo);

    /**
     * 根据主键实现数据查询
     */
    VO selectByPrimaryKey(PK pk);

}
