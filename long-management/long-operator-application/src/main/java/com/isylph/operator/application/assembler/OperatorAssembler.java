package com.isylph.operator.application.assembler;


import com.isylph.operator.api.beans.department.OperatorManagerVO;
import com.isylph.operator.api.beans.operator.SysOperatorSaveCmd;
import com.isylph.operator.api.beans.operator.SysOperatorUpdateCmd;
import com.isylph.operator.api.beans.operator.SysOperatorVO;
import com.isylph.operator.domain.entity.OperatorManager;
import com.isylph.operator.domain.entity.SysOperator;
import com.isylph.operator.domain.types.SysDepartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperatorAssembler {

    @Mapping(target = "orgId", source = "org.id")
    @Mapping(target = "orgName", source = "org.name")
    SysOperatorVO toSysOperatorVO(SysOperator src);
    List<SysOperatorVO> toSysOperatorVO(List<SysOperator> src);

    SysOperator toSysOperator(SysOperatorSaveCmd src);
    SysOperator toSysOperator(SysOperatorUpdateCmd src);


    @Mapping(source = "manager.id", target = "operatorId")
    @Mapping(source = "manager.name", target = "operatorName")
    @Mapping(source = "delegated.id", target = "delegatedId")
    @Mapping(source = "delegated.name", target = "delegatedName")
    @Mapping(source = "src.department.id", target = "departmentId")
    @Mapping(source = "src.remark", target = "remark")
    OperatorManagerVO toOperatorManagerVO(OperatorManager src, SysOperator manager, SysOperator delegated);


    @Mapping(target = "department", source = "department")
    @Mapping(target = "managerId", expression = "java(new com.isylph.operator.domain.types.AccountId(src.getOperatorId()))")
    @Mapping(target = "delegated", expression = "java(new com.isylph.operator.domain.types.AccountId(src.getDelegatedId()))")
    OperatorManager toOperatorManager(OperatorManagerVO src, SysDepartment department);
}
