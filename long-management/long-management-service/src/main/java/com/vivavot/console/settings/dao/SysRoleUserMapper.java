package com.vivavot.console.settings.dao;

import com.vivavot.basis.persistence.dao.BaseMapperEx;
import com.vivavot.console.api.beans.system.role.SysRoleUserVO;
import com.vivavot.console.api.beans.system.role.SysRoleVO;
import com.vivavot.console.api.beans.system.role.SysRoleUserQuery;
import com.vivavot.console.settings.model.SysRoleUserPO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
public interface SysRoleUserMapper
        extends BaseMapperEx<SysRoleUserPO, SysRoleUserQuery> {

    List<SysRoleVO> getUserRoles(Long userId);

    List<SysRoleUserVO> listUserByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);
}
