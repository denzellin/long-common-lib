package com.vivavot.console.settings.dao;

import com.vivavot.basis.persistence.dao.BaseMapperEx;
import com.vivavot.console.api.beans.system.func.SysFuncVO;
import com.vivavot.console.api.beans.system.role.SysRoleVO;
import com.vivavot.console.api.beans.system.role.SysRoleFuncQuery;
import com.vivavot.console.settings.model.SysRoleFuncPO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
public interface SysRoleFuncMapper
        extends BaseMapperEx<SysRoleFuncPO, SysRoleFuncQuery> {

    List<SysFuncVO> listRoleFunc(Long roleId);

    List<SysFuncVO> listAllFunc();

    int deleteByRoleId(Long roleId);

    List<SysFuncVO> listRolesFuncs(List<Long> ids);

    List<SysRoleVO> listRolesByFunc(Long funcId);
}
