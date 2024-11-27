package com.isylph.console.settings.dao;

import com.isylph.basis.persistence.dao.BaseMapperEx;
import com.isylph.console.api.beans.system.role.SysRoleQuery;
import com.isylph.console.settings.model.SysRolePO;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
public interface SysRoleMapper
        extends BaseMapperEx<SysRolePO, SysRoleQuery> {

    @Override
    List<SysRolePO> selectViewListPage(SysRoleQuery query);

    @Override
    int countSelectViewListPage(SysRoleQuery query);
}
