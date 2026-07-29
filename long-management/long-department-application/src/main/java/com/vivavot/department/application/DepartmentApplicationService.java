package com.vivavot.department.application;


import com.vivavot.basis.base.RetPage;
import com.vivavot.department.api.beans.DepartmentQuery;
import com.vivavot.department.api.beans.DepartmentSaveCmd;
import com.vivavot.department.api.beans.DepartmentUpdateCmd;
import com.vivavot.department.api.beans.DepartmentVO;


import java.util.List;

public interface DepartmentApplicationService {

    void saveDepartment(DepartmentSaveCmd req);

    void updateDepartment(DepartmentUpdateCmd req);

    DepartmentVO getDepartment(Long id);

    void deleteDepartment(Long id);

    List<DepartmentVO> getOrgs();

    RetPage<DepartmentVO> listDepartment(DepartmentQuery req);

}
