package com.isylph.console.settings.service.impl;

import com.isylph.console.api.beans.system.menu.SysMenuQuery;
import com.isylph.console.settings.dao.SysMenuMapper;
import com.isylph.console.settings.model.SysMenuPO;
import com.isylph.console.settings.service.SysMenuService;
import com.isylph.service.impl.BaseServiceImpl;
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
