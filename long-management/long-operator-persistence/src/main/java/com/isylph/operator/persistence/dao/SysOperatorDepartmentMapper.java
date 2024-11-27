package com.isylph.operator.persistence.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isylph.operator.persistence.model.SysOperatorDepartmentPO;

import java.util.List;

/**
 * <p>
 * 部门成员 Mapper 接口
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-22
 */
public interface SysOperatorDepartmentMapper extends BaseMapper<SysOperatorDepartmentPO> {

    void deleteByOperatorId(Long id);

    List<SysOperatorDepartmentPO> getOperatorDepartments(Long id);
}
