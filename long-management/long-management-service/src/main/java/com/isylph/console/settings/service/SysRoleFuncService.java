package com.isylph.console.settings.service;

import com.isylph.console.api.beans.system.func.SysFuncVO;
import com.isylph.console.api.beans.system.role.SysRoleVO;
import com.isylph.console.settings.model.SysRoleFuncPO;
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
public interface SysRoleFuncService
        extends BaseService<SysRoleFuncPO> {

    void saveRoleFunc(Long roleId, List<Long> funcIds);

    List<SysFuncVO> listRolesFuncs(List<Long> ids);

    List<SysFuncVO> listRoleFunc(Long roleId);

    List<SysFuncVO> listAllFunc();

    List<SysRoleVO> listRolesByFunc(Long funcId);
}
