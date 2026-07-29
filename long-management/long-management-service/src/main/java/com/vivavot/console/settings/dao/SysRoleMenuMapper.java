package com.vivavot.console.settings.dao;

import com.vivavot.basis.persistence.dao.BaseMapperEx;
import com.vivavot.console.api.beans.system.menu.SysMenuVO;
import com.vivavot.console.api.beans.system.role.SysRoleMenuQuery;
import com.vivavot.console.settings.model.SysRoleMenuPO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
public interface SysRoleMenuMapper
        extends BaseMapperEx<SysRoleMenuPO, SysRoleMenuQuery> {

    List<SysMenuVO> listRoleMenu(Long roleId);

    List<SysMenuVO> listRolesMenus(List<Long> ids);

    List<SysMenuVO> listRolesSysMenus();

    void  deleteByRoleId(Long roleId);
}
