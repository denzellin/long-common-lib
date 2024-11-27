package com.isylph.controller;

import com.isylph.basis.base.BaseListQuery;
import com.isylph.service.BaseService;
import org.springframework.beans.BeanUtils;

/**
 * 通用增删改查控制器
 *
 * @author zhouhao
 * @see QueryController
 * @see CreateController
 * @see UpdateController
 * @see DeleteController
 * @since 3.0
 */
public interface CrudController<CV, UV, Q extends BaseListQuery, V, T>
        extends CreateController<T, CV>
        , DeleteController
        , UpdateController<T, UV>
        , QueryController<T, V, Q> {

    @Override
    @SuppressWarnings("unchecked")
    BaseService<T> getService();

    T createModel();

    V createViewObject();

    @Override
    default T createRequestToModel(CV viewObject) {
        T model = createModel();
        BeanUtils.copyProperties(viewObject, model);
        return model;
    }

    @Override
    default T updateRequestToModel(UV viewObject) {
        T model = createModel();
        BeanUtils.copyProperties(viewObject, model);
        return model;
    }

    @Override
    default V modelToViewObject(T model) {
        V viewObject = createViewObject();
        BeanUtils.copyProperties(model, viewObject);
        return viewObject;
    }
}
