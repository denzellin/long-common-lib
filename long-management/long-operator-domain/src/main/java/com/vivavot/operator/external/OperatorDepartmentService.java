package com.vivavot.operator.external;

import com.vivavot.operator.domain.types.SysDepartment;

public interface OperatorDepartmentService {

    SysDepartment getDepartment(Long deptId);
}
