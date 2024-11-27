package com.isylph.department.repository;


import com.isylph.basis.base.RetPage;
import com.isylph.department.api.beans.DepartmentQuery;
import com.isylph.department.domain.entity.Department;

import java.util.List;

public interface DepartmentRepository {

    void saveDepartment(Department req);

    void updateDepartment(Department req);

    Department getDepartment(Long id);

    void deleteDepartment(Long id);

    List<Department> getOrgs();

    RetPage<Department> listDepartment(DepartmentQuery req);

}
