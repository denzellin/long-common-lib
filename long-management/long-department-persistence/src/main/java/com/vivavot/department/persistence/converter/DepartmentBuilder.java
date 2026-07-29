package com.vivavot.department.persistence.converter;


import com.vivavot.department.domain.entity.Department;
import com.vivavot.department.persistence.model.DepartmentPO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentBuilder {

    Department toDepartment(DepartmentPO src);
    List<Department> toDepartment(List<DepartmentPO> src);

    DepartmentPO toDepartmentPO(Department src);
}
