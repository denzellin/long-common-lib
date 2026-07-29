package com.vivavot.console.settings.service;

import com.vivavot.console.settings.model.SysFuncPO;
import com.vivavot.service.BaseService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
public interface SysFuncService
        extends BaseService<SysFuncPO> {
    List<SysFuncPO> listAll();
}
