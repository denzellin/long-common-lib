package com.isylph.operator.application.impl;


import com.isylph.basis.base.RetPage;
import com.isylph.operator.api.beans.department.OperatorManagerSaveCmd;
import com.isylph.operator.api.beans.department.OperatorManagerVO;
import com.isylph.operator.api.beans.operator.SysOperatorQuery;
import com.isylph.operator.api.beans.operator.SysOperatorSaveCmd;
import com.isylph.operator.api.beans.operator.SysOperatorUpdateCmd;
import com.isylph.operator.api.beans.operator.SysOperatorVO;
import com.isylph.operator.api.beans.service.OperatorInterfaceService;
import com.isylph.operator.application.OperatorApplicationService;
import com.isylph.operator.application.assembler.OperatorAssembler;
import com.isylph.operator.domain.entity.OperatorDepartment;
import com.isylph.operator.domain.entity.OperatorManager;
import com.isylph.operator.domain.entity.SysOperator;
import com.isylph.operator.domain.types.Account;
import com.isylph.operator.domain.types.AccountId;
import com.isylph.operator.domain.types.SysDepartment;
import com.isylph.operator.external.OperatorDepartmentService;
import com.isylph.operator.repository.OperatorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OperatorApplicationServiceImpl implements OperatorApplicationService, OperatorInterfaceService {

    @Autowired
    private OperatorAssembler operatorAssembler;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private OperatorDepartmentService operatorDepartmentService;

    @Override
    public SysOperatorVO login(String account, String password) {
        SysOperator so = operatorRepository.findOperator(new Account(account));
        if (so == null){
            return null;
        }

        if (so.login(password)){
            return operatorAssembler.toSysOperatorVO(so);
        }
        return null;
    }

    @Override
    public Long createOperator(SysOperatorSaveCmd req) {
        SysOperator so = operatorAssembler.toSysOperator(req);
        so.setOrg(
                operatorDepartmentService.getDepartment(req.getOrgId())
        );
        so.initPassword(req.getPassword());
        return operatorRepository.saveOperator(so);
    }

    @Override
    public void updateOperator(SysOperatorUpdateCmd req) {
        SysOperator so = operatorAssembler.toSysOperator(req);
        so.setOrg(
                operatorDepartmentService.getDepartment(req.getOrgId())
        );

        SysOperator origin = operatorRepository.findOperator(new AccountId(req.getId()));
        origin.update(so);

        if(req.getDepartments() != null){
            List<OperatorDepartment> ods = new ArrayList<>();
            req.getDepartments().stream().forEach(d->{
                Optional.ofNullable(operatorDepartmentService.getDepartment(d.getDepartmentId())).ifPresent(dept->{
                    OperatorDepartment od = new OperatorDepartment();
                    od.setDepartmentId(dept.getId())
                            .setDepartmentName(dept.getName())
                            .setOrgCode(dept.getCode())
                            .setPriority(d.getPriority())
                            .setRemark(d.getRemark());
                    ods.add(od);
                });

            });

            so.setDepartments(ods);
        }

        operatorRepository.saveOperator(so);
    }

    @Override
    public SysOperatorVO getOperator(Long accountId) {
        return operatorAssembler.toSysOperatorVO(
                operatorRepository.findOperator(new AccountId(accountId))
        );
    }

    @Override
    public SysOperatorVO getOperator(String account) {
        return operatorAssembler.toSysOperatorVO(
                operatorRepository.findOperator(new Account(account))
        );
    }

    @Override
    public RetPage<SysOperatorVO> queryOperator(SysOperatorQuery req) {

        RetPage<SysOperator> rs = operatorRepository.queryOperator(req);

        RetPage<SysOperatorVO> ret = new RetPage<>();
        if(rs.getTotal() > 0){
            ret.setRecords(operatorAssembler.toSysOperatorVO(rs.getRecords()));
        }
        ret.setTotal(rs.getTotal());
        return ret;
    }

    @Override
    public void removeOperator(Long accountId) {
        operatorRepository.removeOperator(new AccountId(accountId));
    }

    @Override
    public void updatePassword(Long accountId, String newz) {
        SysOperator so = operatorRepository.findOperator(new AccountId(accountId));
        if (so == null){
            return;
        }

        so.initPassword(newz);
        operatorRepository.saveOperator(so);
    }

    @Override
    public List<OperatorManagerVO> queryOperatorManger(Long deptId) {
        List<OperatorManager> ms = operatorRepository.getOperatorManagers(deptId);
        if (CollectionUtils.isEmpty(ms)){
            return null;
        }
        List<OperatorManagerVO> vs = ms.stream().map(m -> {
            SysOperator manager = operatorRepository.findOperator(m.getManagerId());
            SysOperator delegated = operatorRepository.findOperator(m.getDelegated());
            return operatorAssembler.toOperatorManagerVO(m, manager, delegated);
        }).collect(Collectors.toList());

        return vs;
    }

    @Override
    public void saveOperatorManger(OperatorManagerSaveCmd req) {

        SysDepartment department = operatorDepartmentService.getDepartment(req.getDepartmentId());
        if (department == null ){
            return;
        }
        if (!CollectionUtils.isEmpty(req.getManagers())){
            List<OperatorManager> ms = req.getManagers().stream().map(m->{
               return operatorAssembler.toOperatorManager(m, department);
            }).collect(Collectors.toList());
            operatorRepository.saveOperatorManagers(ms);
        }else{
            operatorRepository.removeOperatorManagers(department.getId());
        }

    }
}
