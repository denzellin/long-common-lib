package com.isylph.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isylph.basis.base.BaseListQuery;

import java.util.List;

/**
 * @param <T> 数据实体
 * @param <V> 视图对象
 * @param <Q> 列表查询对象
 */
public interface BaseMapperExt<T, V, Q extends BaseListQuery> extends BaseMapper<T> {

    List<V> selectViewListPage(Q query);

    int countSelectViewListPage(Q query);

}
