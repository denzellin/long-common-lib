package com.isylph.operator.domain.entity;


import com.isylph.operator.domain.types.AccountId;
import com.isylph.operator.domain.types.SysDepartment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 部门主管配置
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OperatorManager {


    private SysDepartment department;

    /**
     * 账号ID
     */
    private AccountId managerId;

    /**
     * 角色
     */
    private String role;

    /**
     * 权限优先级，数值倒序
     */
    private Integer priority;

    /**
     * 被委托人
     */
    private AccountId delegated;

    private String remark;


}
