package com.vivavot.operator.external.impl;

import com.vivavot.department.api.beans.DepartmentVO;
import com.vivavot.department.application.DepartmentApplicationService;
import com.vivavot.operator.domain.types.SysDepartment;
import com.vivavot.operator.external.OperatorDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OperatorDepartmentServiceImpl implements OperatorDepartmentService {

    @Autowired
    private DepartmentApplicationService departmentApplication;

    @Override
    public SysDepartment getDepartment(Long departmentId) {
        if (departmentId == null){
            return null;
        }
        DepartmentVO org = departmentApplication.getDepartment(departmentId);
        if (null == org){

            log.info("department not found: {}", departmentId);
            return null;
        }

        return new SysDepartment(org.getId(), org.getTitle(), org.getCode());
    }
}
