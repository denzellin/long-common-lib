
package com.isylph.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isylph.basis.beans.HttpRetData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 新增控制器
 *
 * @param <T>  实体对象
 * @param <CV> 新增视图对象
 */
public interface CreateController<T, CV> {

    <S extends IService<T>> S getService();

    @PostMapping("/c")
    default HttpRetData add(@RequestBody CV request) {
        T model = createRequestToModel(request);
        getService().save(model);
        return HttpRetData.success();
    }

    T createRequestToModel(CV create);
}
