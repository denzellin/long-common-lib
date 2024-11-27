package com.isylph.department.api.service;

import com.isylph.basis.base.RetPage;
import com.isylph.department.api.beans.DepartmentQuery;
import com.isylph.department.api.beans.DepartmentVO;

import java.util.List;

public interface DepartmentInterfaceService {

    DepartmentVO getDepartment(Long id);

    List<DepartmentVO> getOrgs();

    RetPage<DepartmentVO> listDepartment(DepartmentQuery req);
}
