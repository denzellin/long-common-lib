package com.isylph.operator.persistence.converter;


import com.isylph.operator.domain.entity.OperatorDepartment;
import com.isylph.operator.domain.entity.OperatorManager;
import com.isylph.operator.domain.entity.SysOperator;
import com.isylph.operator.domain.types.SysDepartment;
import com.isylph.operator.persistence.model.SysOperatorDepartmentPO;
import com.isylph.operator.persistence.model.SysOperatorManagerPO;
import com.isylph.operator.persistence.model.SysOperatorPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperatorBuilder {

    @Mapping(target = "orgId", source = "org.id")
    @Mapping(target = "orgCode", source = "org.code")
    SysOperatorPO toSysOperatorPO(SysOperator src);

    SysOperator toSysOperator(SysOperatorPO src);
    List<SysOperator> toSysOperator(List<SysOperatorPO> src);


    @Mapping(target = "memberId", source = "memberId")
    SysOperatorDepartmentPO toSysOperatorDepartmentPO (OperatorDepartment src, Long memberId);

    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "orgCode", source = "department.code")
    OperatorDepartment toSysOperatorDepartment (SysOperatorDepartmentPO src, SysDepartment department);

    @Mapping(target = "operatorId", source = "managerId.accountId")
    @Mapping(target = "delegatedId", source = "delegated.accountId")
    @Mapping(target = "departmentId", source = "department.id")
    SysOperatorManagerPO toOperatorManagerPO(OperatorManager src);

    @Mapping(target = "managerId", expression = "java(new com.isylph.operator.domain.types.AccountId(src.getOperatorId()))")
    @Mapping(target = "delegated", expression = "java(new com.isylph.operator.domain.types.AccountId(src.getDelegatedId()))")
    @Mapping(target = "department", source = "department")
    OperatorManager toOperatorManager(SysOperatorManagerPO src, SysDepartment department);

}
