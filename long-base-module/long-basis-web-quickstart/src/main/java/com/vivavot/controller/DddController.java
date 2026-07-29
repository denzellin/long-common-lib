package com.vivavot.controller;


import com.vivavot.basis.base.BaseListQuery;
import com.vivavot.service.BaseService;

public interface DddController<CV, UV, Q extends BaseListQuery, V, T> extends CrudController<CV, UV, Q, V, T> {

    @Override
    default BaseService<T> getService(){
        return null;
    }

    @Override
    default T createModel(){
        return null;
    }

    @Override
    default V createViewObject(){
        return null;
    }
}
