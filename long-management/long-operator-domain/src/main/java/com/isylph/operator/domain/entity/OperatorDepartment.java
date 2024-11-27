package com.isylph.operator.domain.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 部门成员
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OperatorDepartment {

    /**
     * 部门ID
     */
    private Long departmentId;

    String departmentName;

    String orgCode;

    /**
     * 权限优先级，数值倒序
     */
    private Integer priority;

    private String remark;


}
