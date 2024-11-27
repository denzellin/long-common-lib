package com.isylph.operator.api.beans.department;

import com.isylph.basis.beans.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 部门成员
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OperatorDepartmentVO extends BaseDTO {

    private Long departmentId;

    private String departmentName;

    private Long memberId;

    private String memberName;

    private Integer priority;

    private String remark;


}
