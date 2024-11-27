package com.isylph.console.settings.dao;

import com.isylph.basis.persistence.dao.BaseMapperEx;
import com.isylph.console.api.beans.system.menu.SysMenuVO;
import com.isylph.console.api.beans.system.role.SysRoleMenuQuery;
import com.isylph.console.settings.model.SysRoleMenuPO;

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
