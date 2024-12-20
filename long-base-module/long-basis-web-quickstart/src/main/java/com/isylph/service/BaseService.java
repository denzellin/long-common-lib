package com.isylph.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isylph.basis.base.BaseListQuery;
import com.isylph.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @param <T> 数据实体
 */
public interface BaseService<T> extends IService<T> {


    /**
     * 根据条件分页查询视图对象
     *
     * @param query 筛选条件
     * @return 视图对象列表
     */
    Page<T> selectViewListPage(BaseListQuery query);

    /**
     * 根据条件统计数量
     * @param query 筛选条件
     * @return
     */
    Integer countSelectViewListPage(BaseListQuery query);




}
