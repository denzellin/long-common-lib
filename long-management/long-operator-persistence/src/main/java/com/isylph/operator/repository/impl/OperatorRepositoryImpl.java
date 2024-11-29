package com.isylph.operator.repository.impl;


import com.isylph.basis.base.RetPage;
import com.isylph.basis.consts.BaseErrorConsts;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.operator.api.beans.operator.SysOperatorQuery;
import com.isylph.operator.domain.entity.OperatorManager;
import com.isylph.operator.domain.entity.SysOperator;
import com.isylph.operator.domain.types.Account;
import com.isylph.operator.domain.types.AccountId;
import com.isylph.operator.domain.types.SysDepartment;
import com.isylph.operator.external.OperatorDepartmentService;
import com.isylph.operator.persistence.converter.OperatorBuilder;
import com.isylph.operator.persistence.dao.SysOperatorDepartmentMapper;
import com.isylph.operator.persistence.dao.SysOperatorManagerMapper;
import com.isylph.operator.persistence.dao.SysOperatorMapper;
import com.isylph.operator.persistence.model.SysOperatorDepartmentPO;
import com.isylph.operator.persistence.model.SysOperatorManagerPO;
import com.isylph.operator.persistence.model.SysOperatorPO;
import com.isylph.operator.repository.OperatorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class OperatorRepositoryImpl implements OperatorRepository {


    @Autowired
    private SysOperatorMapper sysOperatorMapper;

    @Autowired
    private SysOperatorDepartmentMapper sysOperatorDepartmentMapper;

    @Autowired
    private SysOperatorManagerMapper sysOperatorManagerMapper;

    @Autowired
    private OperatorDepartmentService operatorDepartmentService;

    @Autowired
    private OperatorBuilder operatorBuilder;

    @Transactional
    @Override
    public synchronized Long saveOperator(SysOperator req) {

        SysOperatorPO po = operatorBuilder.toSysOperatorPO(req);


        if (!CollectionUtils.isEmpty(req.getDepartments())){
            po.setOrgCode(req.getDepartments().get(0).getOrgCode());
        }

        SysOperatorPO exist = sysOperatorMapper.getByAccount(req.getAccount());
        if (po.getId() == null){
            if (exist != null){
                log.info("invalid account： {}", req);
                throw new ReturnException(BaseErrorConsts.RET_CONFLICT);
            }
            sysOperatorMapper.insert(po);

        }else{
            if (exist != null && !exist.getId().equals(req.getId())){
                log.info("invalid account： {}", req);
                throw new ReturnException(BaseErrorConsts.RET_CONFLICT);
            }
            sysOperatorMapper.updateById(po);
            sysOperatorDepartmentMapper.deleteByOperatorId(po.getId());
        }

        if (CollectionUtils.isEmpty(req.getDepartments())){
            return po.getId();
        }

        req.getDepartments().stream().forEach(d->{
            SysOperatorDepartmentPO dp = operatorBuilder.toSysOperatorDepartmentPO(d, po.getId());
            sysOperatorDepartmentMapper.insert(dp);
        });

        return po.getId();

    }

    private SysOperator buildSysOperator(SysOperatorPO po){
        SysOperator o = operatorBuilder.toSysOperator(po);
        o.setOrg(
                operatorDepartmentService.getDepartment(po.getOrgId())
        );

        return o;
    }

    private void getDepartments(SysOperator so){
        List<SysOperatorDepartmentPO> depts = sysOperatorDepartmentMapper.getOperatorDepartments(so.getId());
        if (!CollectionUtils.isEmpty(depts)){
            so.setDepartments(depts.stream().map(d->{
                SysDepartment dept = operatorDepartmentService.getDepartment(d.getDepartmentId());
                return operatorBuilder.toSysOperatorDepartment(d, dept);
            }).collect(Collectors.toList()));
        }
    }

    @Override
    public RetPage<SysOperator> queryOperator(SysOperatorQuery req) {
        RetPage<SysOperator> ret = new RetPage<>();
        int cnt = sysOperatorMapper.countSelectViewListPage(req);
        if (cnt > 0){
            List<SysOperatorPO> ps = sysOperatorMapper.selectViewListPage(req);
            ret.setRecords(ps.stream().map(s -> {
                return buildSysOperator(s);
            }).collect(Collectors.toList()));
        }
        ret.setTotal(cnt);
        return ret;
    }

    @Override
    public SysOperator findOperator(Account account) {
        SysOperatorPO po = sysOperatorMapper.getByAccount(account.getAccount());
        if(po == null){
            return null;
        }
        SysOperator so = buildSysOperator(po);
        getDepartments(so);

        return so;
    }

    @Override
    public SysOperator findOperator(AccountId accountId) {
        SysOperatorPO po = sysOperatorMapper.getByAccountId(accountId.getAccountId());
        if(po == null){
            return null;
        }
        SysOperator so = buildSysOperator(po);
        getDepartments(so);

        return so;
    }

    @Transactional
    @Override
    public void removeOperator(AccountId id) {
        sysOperatorMapper.deleteById(id.getAccountId());
        sysOperatorDepartmentMapper.deleteByOperatorId(id.getAccountId());
    }

    @Override
    public List<OperatorManager> getOperatorManagers(Long deptId) {
        List<SysOperatorManagerPO> ps = sysOperatorManagerMapper.getByDepartment(deptId);

        if (CollectionUtils.isEmpty(ps)){

            return null;
        }
        List<OperatorManager> ops = ps.stream().map(p->{
            SysDepartment department = operatorDepartmentService.getDepartment(p.getDepartmentId());
            return operatorBuilder.toOperatorManager(p, department);
        }).collect(Collectors.toList());

        return ops;
    }

    @Transactional
    @Override
    public void saveOperatorManagers(List<OperatorManager> req) {
        sysOperatorManagerMapper.deleteByDepartment(req.get(0).getDepartment().getId());

        req.stream().forEach(d->{
            SysOperatorManagerPO p = operatorBuilder.toOperatorManagerPO(d);
            sysOperatorManagerMapper.insert(p);
        });
    }

    @Override
    public void removeOperatorManagers(Long deptId) {
        sysOperatorManagerMapper.deleteByDepartment(deptId);
    }
}
