package com.isylph.operator.api.beans.department;

import com.isylph.basis.beans.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 部门主管配置
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OperatorManagerVO extends BaseDTO {

    private Long departmentId;

    private Long operatorId;

    private String operatorName;

    private String role;

    private Integer priority;

    private Long delegatedId;

    private String delegatedName;

    private String remark;


}
