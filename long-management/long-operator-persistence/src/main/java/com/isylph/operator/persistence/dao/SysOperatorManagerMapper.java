package com.isylph.operator.persistence.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isylph.operator.persistence.model.SysOperatorManagerPO;

import java.util.List;

/**
 * <p>
 * 部门主管配置 Mapper 接口
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-22
 */
public interface SysOperatorManagerMapper extends BaseMapper<SysOperatorManagerPO> {

    List<SysOperatorManagerPO> getByDepartment(Long deptId);

    void deleteByDepartment(Long deptId);
}
