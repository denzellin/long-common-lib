package com.isylph.console.settings.service;


import com.isylph.console.api.beans.system.menu.SysMenuVO;
import com.isylph.console.settings.model.SysRoleMenuPO;
import com.isylph.service.BaseService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
public interface SysRoleMenuService
        extends BaseService<SysRoleMenuPO> {

    List<SysMenuVO> listRoleMenu(Long roleId);

    List<SysMenuVO> listRolesMenus(List<Long> ids);

    void saveRoleMenu(Long roleId, List<Long> menuIds);
}
