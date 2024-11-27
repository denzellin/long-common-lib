package com.isylph.console.settings.service.impl;

import com.isylph.console.api.beans.system.role.SysRoleQuery;
import com.isylph.console.settings.service.SysRoleService;
import com.isylph.console.settings.dao.SysRoleMapper;
import com.isylph.console.settings.model.SysRolePO;
import com.isylph.service.impl.BaseServiceImpl;
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
