package com.isylph.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isylph.basis.base.BaseListQuery;
import com.isylph.basis.beans.HttpRetData;
import com.isylph.basis.consts.RetCodeConsts;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.service.BaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用查询控制器。
 *
 * @param <T> 实体类型
 * @param <V> 视图对象
 * @param <Q> 列表查询对象
 */
public interface QueryController<T, V, Q extends BaseListQuery> {

    <S extends BaseService<T>> S getService();

    V modelToViewObject(T model);

    @GetMapping(path = {"/r/{id}"})
    default HttpRetData<V> getByPrimaryKey(@PathVariable(name = "id") Long id) {

        T model = getService().getById(id);
        if(null == model){
            throw new ReturnException(RetCodeConsts.RET_NOT_FOUND);
        }
        V view = modelToViewObject(model);
        return HttpRetData.success(view);

    }

    @PostMapping(path = "/r")
    default HttpRetData<List<V>> listByFilter(@RequestBody Q query) {
        return list(query);
    }

    default HttpRetData<List<V>> list(Q query) {
        Page<T> page = getService().selectViewListPage(query);
        return changePage(page);
    }

    default HttpRetData<List<V>> changePage(Page<T> page) {
        if (page == null) {
            return HttpRetData.success(new ArrayList<>(), 0);
        }
        List<V> vs = new ArrayList<>();
        for (T t: page.getRecords()){
            V view = modelToViewObject(t);
            vs.add(view);
        }
        return HttpRetData.success(vs, (int)page.getTotal());
    }
}
