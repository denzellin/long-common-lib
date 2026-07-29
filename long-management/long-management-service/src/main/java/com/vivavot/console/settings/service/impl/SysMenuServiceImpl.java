package com.vivavot.console.settings.service.impl;

import com.vivavot.console.api.beans.system.menu.SysMenuQuery;
import com.vivavot.console.settings.dao.SysMenuMapper;
import com.vivavot.console.settings.model.SysMenuPO;
import com.vivavot.console.settings.service.SysMenuService;
import com.vivavot.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
@Service
public class SysMenuServiceImpl
        extends BaseServiceImpl<
                SysMenuMapper,
                SysMenuPO,
        SysMenuQuery>
        implements SysMenuService {

    @Override
    public int batchUpdateCode(HashMap<String, String> filter) {
        return baseMapper.batchUpdateCode(filter);
    }
}
