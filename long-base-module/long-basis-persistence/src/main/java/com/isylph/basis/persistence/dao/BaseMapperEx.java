package com.isylph.basis.persistence.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @param <T> 数据实体
 * @param <Q> 列表查询对象
 */
public interface BaseMapperEx<T, Q> extends BaseMapper<T> {

    List<T> selectViewListPage(Q query);

    int countSelectViewListPage(Q query);

}
