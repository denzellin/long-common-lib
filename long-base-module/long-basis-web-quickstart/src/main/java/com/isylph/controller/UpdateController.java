package com.isylph.controller;


import com.baomidou.mybatisplus.extension.service.IService;
import com.isylph.basis.beans.HttpRetData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 通用更新控制器
 *
 * @author zhouhao
 */
public interface UpdateController<T, UV> {

    <S extends IService<T>> S getService();

    @PostMapping(path = {"/u"})
    default HttpRetData updateByPrimaryKey(@RequestBody UV request) {
        T model = updateRequestToModel(request);
        getService().updateById(model);
        return HttpRetData.success();
    }

    T updateRequestToModel(UV viewObject);
}
