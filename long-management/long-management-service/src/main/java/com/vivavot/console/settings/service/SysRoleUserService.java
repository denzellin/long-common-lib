package com.vivavot.console.settings.service;


import com.vivavot.console.api.beans.system.role.SysRoleVO;
import com.vivavot.console.settings.model.SysRoleUserPO;
import com.vivavot.operator.api.beans.operator.SysOperatorVO;
import com.vivavot.service.BaseService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
public interface SysRoleUserService
        extends BaseService<SysRoleUserPO> {

    List<SysRoleVO> getUserRoles(Long userId);

    List<SysOperatorVO> listUserByRoleId(Long roleId);

    void saveRoleMember(Long roleId, List<Long>userIds);

    void addRoleMember(Long roleId, Long userId);
}
