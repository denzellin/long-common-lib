package com.isylph.controller;


import com.isylph.basis.base.BaseListQuery;
import com.isylph.service.BaseService;

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
