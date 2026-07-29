package com.vivavot.console.settings.service;

import com.vivavot.console.settings.model.SysMenuPO;
import com.vivavot.service.BaseService;

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
