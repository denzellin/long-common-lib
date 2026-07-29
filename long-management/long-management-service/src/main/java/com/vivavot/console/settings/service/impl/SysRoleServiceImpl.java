package com.vivavot.console.settings.service.impl;

import com.vivavot.console.api.beans.system.role.SysRoleQuery;
import com.vivavot.console.settings.service.SysRoleService;
import com.vivavot.console.settings.dao.SysRoleMapper;
import com.vivavot.console.settings.model.SysRolePO;
import com.vivavot.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
@Service
public class SysRoleServiceImpl
        extends BaseServiceImpl<
                SysRoleMapper,
                SysRolePO,
        SysRoleQuery>
        implements SysRoleService {

}
