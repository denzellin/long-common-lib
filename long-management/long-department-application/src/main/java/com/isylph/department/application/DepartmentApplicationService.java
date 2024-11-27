package com.isylph.department.application;


import com.isylph.basis.base.RetPage;
import com.isylph.department.api.beans.DepartmentQuery;
import com.isylph.department.api.beans.DepartmentSaveCmd;
import com.isylph.department.api.beans.DepartmentUpdateCmd;
import com.isylph.department.api.beans.DepartmentVO;


import java.util.List;

public interface DepartmentApplicationService {

    void saveDepartment(DepartmentSaveCmd req);

    void updateDepartment(DepartmentUpdateCmd req);

    DepartmentVO getDepartment(Long id);

    void deleteDepartment(Long id);

    List<DepartmentVO> getOrgs();

    RetPage<DepartmentVO> listDepartment(DepartmentQuery req);

}
