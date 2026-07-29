package com.vivavot.department.api.service;

import com.vivavot.basis.base.RetPage;
import com.vivavot.department.api.beans.DepartmentQuery;
import com.vivavot.department.api.beans.DepartmentVO;

import java.util.List;

public interface DepartmentInterfaceService {

    DepartmentVO getDepartment(Long id);

    List<DepartmentVO> getOrgs();

    RetPage<DepartmentVO> listDepartment(DepartmentQuery req);
}
