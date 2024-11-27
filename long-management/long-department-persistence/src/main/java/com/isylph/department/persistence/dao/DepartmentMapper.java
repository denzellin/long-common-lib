package com.isylph.department.persistence.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isylph.basis.persistence.dao.BaseMapperEx;
import com.isylph.department.api.beans.DepartmentQuery;
import com.isylph.department.persistence.model.DepartmentPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 部门组织结构, 部门主管等角色定义在manager表中 Mapper 接口
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-22
 */
public interface DepartmentMapper extends BaseMapperEx<DepartmentPO, DepartmentQuery> {

    List<DepartmentPO> selectHierarchy(@Param("deptIds") List<Long> deptIds);

}
