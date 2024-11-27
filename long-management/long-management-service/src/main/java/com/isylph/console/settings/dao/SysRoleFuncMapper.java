package com.isylph.console.settings.dao;

import com.isylph.basis.persistence.dao.BaseMapperEx;
import com.isylph.console.api.beans.system.func.SysFuncVO;
import com.isylph.console.api.beans.system.role.SysRoleVO;
import com.isylph.console.api.beans.system.role.SysRoleFuncQuery;
import com.isylph.console.settings.model.SysRoleFuncPO;

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
