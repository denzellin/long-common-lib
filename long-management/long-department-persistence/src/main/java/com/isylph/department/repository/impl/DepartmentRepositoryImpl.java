package com.isylph.department.repository.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.isylph.basis.base.RetPage;
import com.isylph.basis.beans.Tree;
import com.isylph.basis.consts.BaseErrorConsts;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.department.api.beans.DepartmentQuery;
import com.isylph.department.api.beans.DepartmentVO;
import com.isylph.department.domain.entity.Department;
import com.isylph.department.persistence.converter.DepartmentBuilder;
import com.isylph.department.persistence.dao.DepartmentMapper;
import com.isylph.department.persistence.model.DepartmentPO;
import com.isylph.department.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {


    @Autowired
    private DepartmentMapper departmentMapper;


    @Autowired
    private DepartmentBuilder departmentBuilder;


    private String getDeptCode(DepartmentPO dept) {
        Long fid = dept.getFid();
        if (null == fid || 0 == fid) {
            return ".000." + dept.getId() + ".";
        }else {
            DepartmentPO f = departmentMapper.selectById(fid);
            if (null == f) {
                throw new ReturnException(BaseErrorConsts.RET_NOT_FOUND);
            }

            return f.getCode() + dept.getId() + ".";
        }
    }

    @Transactional
    @Override
    public void saveDepartment(Department req) {
        DepartmentPO po = departmentBuilder.toDepartmentPO(req);
        departmentMapper.insert(po);
        po.setCode(getDeptCode(po));
        departmentMapper.updateById(po);
    }

    @Transactional
    @Override
    public void updateDepartment(Department req) {
        DepartmentPO po = departmentBuilder.toDepartmentPO(req);
        po.setCode(getDeptCode(po));
        departmentMapper.updateById(po);
    }

    @Override
    public Department getDepartment(Long id) {

        DepartmentPO po = departmentMapper.selectById(id);
        if (po == null){
            return null;
        }
        Department dept = departmentBuilder.toDepartment(po);
        List<Long> ids = Tree.getIdsFromCode(po.getCode());
        List<DepartmentPO> depts = departmentMapper.selectHierarchy(ids);

        List<String> names = new ArrayList<>();
        if (!CollectionUtils.isEmpty(depts)){
            for(int i = 0; i < depts.size(); i++){
                names.add(depts.get(i).getTitle());
            }
        }else{
            names.add(dept.getTitle());
        }

        dept.setHierarchyTitles(names);
        return dept;
    }

    @Transactional
    @Override
    public void deleteDepartment(Long id) {
        departmentMapper.deleteById(id);
    }

    @Override
    public List<Department> getOrgs() {
        List<DepartmentVO> vos = null;
        QueryWrapper<DepartmentPO> wrapper = Wrappers.query();
        wrapper.eq("fid", 0L);

        long cnt = departmentMapper.selectCount(wrapper);
        if (cnt > 0L){
            List<DepartmentPO> ret = departmentMapper.selectList(wrapper);
            if (!CollectionUtils.isEmpty(ret)){
                return departmentBuilder.toDepartment(ret);
            }
        }

        return null;
    }

    @Override
    public RetPage<Department> listDepartment(DepartmentQuery req) {

        RetPage<Department> ret = new RetPage<>();

        /*QueryWrapper<DepartmentPO> wrapper = Wrappers.query();
        if (StringUtils.isNotEmpty(req.getName())){
            wrapper.like("title", req.getName());
        }

        if (req.getFid() == null && req.getOrgId() != null){
            req.setFid(req.getOrgId());
        }
        if (req.getFid() != null){
            DepartmentPO f = departmentMapper.selectById(req.getFid());
            if (f != null){
                wrapper.likeRight("code", f.getCode());
                wrapper.ne("code", f.getCode());
            }
        }*/


        if (req.getFid() != null){
            DepartmentPO f = departmentMapper.selectById(req.getFid());
            if (f != null){
                req.setCode(f.getCode());
            }
        }

        int cnt = departmentMapper.countSelectViewListPage(req);
        if (cnt > 0){
            List<DepartmentPO> r = departmentMapper.selectViewListPage(req);
            if (!CollectionUtils.isEmpty(r)){
                ret.setRecords(departmentBuilder.toDepartment(r));
            }
        }

        ret.setTotal(cnt);

        return ret;
    }
}
