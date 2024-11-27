package com.isylph.department.application.impl;


import com.isylph.basis.base.RetPage;
import com.isylph.basis.beans.Tree;
import com.isylph.department.api.beans.DepartmentQuery;
import com.isylph.department.api.beans.DepartmentSaveCmd;
import com.isylph.department.api.beans.DepartmentUpdateCmd;
import com.isylph.department.api.beans.DepartmentVO;
import com.isylph.department.api.service.DepartmentInterfaceService;
import com.isylph.department.application.DepartmentApplicationService;
import com.isylph.department.application.assembler.DepartmentAssembler;
import com.isylph.department.domain.entity.Department;
import com.isylph.department.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Slf4j
@Service
public class DepartmentApplicationServiceImpl implements DepartmentApplicationService, DepartmentInterfaceService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentAssembler departmentAssembler;


    @Override
    public void saveDepartment(DepartmentSaveCmd req) {
        Department dept = departmentAssembler.toDepartment(req);
        departmentRepository.saveDepartment(dept);
    }

    @Override
    public void updateDepartment(DepartmentUpdateCmd req) {
        Department dept = departmentAssembler.toDepartment(req);

        departmentRepository.updateDepartment(dept);
    }

    @Override
    public DepartmentVO getDepartment(Long id) {

        Department dept = departmentRepository.getDepartment(id);
        if (dept == null){
            return null;
        }
        DepartmentVO vo =departmentAssembler.toDepartmentVo(dept);
        List<Long> ids = Tree.getIdsFromCode(dept.getCode());
        if (!CollectionUtils.isEmpty(ids)){
            vo.setOrgId(ids.get(1));
        }
        return vo;
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteDepartment(id);
    }

    @Override
    public List<DepartmentVO> getOrgs() {
        List<Department> orgs = departmentRepository.getOrgs();

        return departmentAssembler.toDepartmentVo(orgs);
    }

    @Override
    public RetPage<DepartmentVO> listDepartment(DepartmentQuery req) {

        RetPage<DepartmentVO> ret = new RetPage<>();

        RetPage<Department> r = departmentRepository.listDepartment(req);
        ret.setTotal(r.getTotal());
        if(ret.getTotal() > 0){
            ret.setRecords(departmentAssembler.toDepartmentVo(r.getRecords()));
        }
        return ret;
    }
}
