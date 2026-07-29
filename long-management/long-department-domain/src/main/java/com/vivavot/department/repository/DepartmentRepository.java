package com.vivavot.department.repository;


import com.vivavot.basis.base.RetPage;
import com.vivavot.department.api.beans.DepartmentQuery;
import com.vivavot.department.domain.entity.Department;

import java.util.List;

public interface DepartmentRepository {

    void saveDepartment(Department req);

    void updateDepartment(Department req);

    Department getDepartment(Long id);

    void deleteDepartment(Long id);

    List<Department> getOrgs();

    RetPage<Department> listDepartment(DepartmentQuery req);

}
