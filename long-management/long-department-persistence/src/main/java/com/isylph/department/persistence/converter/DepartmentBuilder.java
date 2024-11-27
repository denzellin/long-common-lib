package com.isylph.department.persistence.converter;


import com.isylph.department.domain.entity.Department;
import com.isylph.department.persistence.model.DepartmentPO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentBuilder {

    Department toDepartment(DepartmentPO src);
    List<Department> toDepartment(List<DepartmentPO> src);

    DepartmentPO toDepartmentPO(Department src);
}
