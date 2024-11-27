package com.isylph.console.settings.service;

import com.isylph.console.settings.model.SysMenuPO;
import com.isylph.service.BaseService;

import java.util.HashMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
public interface SysMenuService
        extends BaseService<SysMenuPO> {

    int batchUpdateCode(HashMap<String, String> filter);
}
