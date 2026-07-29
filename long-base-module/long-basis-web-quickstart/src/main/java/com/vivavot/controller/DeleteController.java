package com.vivavot.controller;


import com.vivavot.basis.beans.HttpRetData;
import com.vivavot.service.BaseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 通用删除控制器
 *
 */
public interface DeleteController {

    <S extends BaseService> S getService();

    @PostMapping(path = "/d")
    default HttpRetData deleteByPrimaryKey(@RequestParam Long id) {
        getService().removeById(id);
        return HttpRetData.success();
    }
}
