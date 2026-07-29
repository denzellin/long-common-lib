package com.vivavot.console.settings.dao;

import com.vivavot.basis.persistence.dao.BaseMapperEx;
import com.vivavot.console.api.beans.system.menu.SysMenuQuery;
import com.vivavot.console.settings.model.SysMenuPO;


import java.util.HashMap;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
public interface SysMenuMapper
        extends BaseMapperEx<SysMenuPO, SysMenuQuery> {

    int batchUpdateCode(HashMap<String, String> filter);
}
