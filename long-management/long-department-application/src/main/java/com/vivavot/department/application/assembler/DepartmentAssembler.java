package com.vivavot.department.application.assembler;


import com.vivavot.department.api.beans.DepartmentSaveCmd;
import com.vivavot.department.api.beans.DepartmentUpdateCmd;
import com.vivavot.department.api.beans.DepartmentVO;
import com.vivavot.department.domain.entity.Department;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentAssembler {

    Department toDepartment(DepartmentSaveCmd dept);

    Department toDepartment(DepartmentUpdateCmd dept);

    DepartmentVO toDepartmentVo(Department src);
    List<DepartmentVO> toDepartmentVo(List<Department> src);
}
