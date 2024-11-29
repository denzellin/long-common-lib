package com.isylph.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isylph.basis.base.BaseListQuery;
import com.isylph.basis.consts.BaseErrorConsts;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.basis.persistence.dao.BaseMapperEx;
import com.isylph.service.BaseService;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <M> mapper接口
 * @param <T> 数据实体
 * @param <Q> 列表查询对象
 */
public abstract class BaseServiceImpl<M extends BaseMapperEx<T,  Q>, T, Q
        extends BaseListQuery> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public boolean removeById(Serializable id) {
        Integer cnt = baseMapper.deleteById(id);
        if (cnt == null || cnt <= 0) {
            throw new ReturnException(getNotExistCode());
        }
        return true;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Page<T> selectViewListPage(BaseListQuery query) {

        Page<T> page = new Page<T>();
        Integer cnt = baseMapper.countSelectViewListPage((Q)query);
        page.setTotal(cnt.longValue());
        if (cnt > 0){

            if (null == query.getOffset()){
                query.setOffset(0);
            }

            if (null == query.getCnt()){
                query.setCnt(100000);
            }

            List<T> resultList = baseMapper.selectViewListPage((Q) query);
            page.setRecords(resultList);
        }
        return page;
    }

    @Override
    public Integer countSelectViewListPage(BaseListQuery query) {
        return baseMapper.countSelectViewListPage((Q)query);
    }


    protected Map<String, Object> getQueryMap() {
        return new HashMap<>();
    }


    protected long getNotExistCode() {
        return BaseErrorConsts.RET_NOT_FOUND;
    }

    protected long getDuplicateItemCode() {
        return BaseErrorConsts.RET_CONFLICT;
    }

    protected long getAlreadyExistCode() {
        return BaseErrorConsts.RET_CONFLICT;
    }
}
