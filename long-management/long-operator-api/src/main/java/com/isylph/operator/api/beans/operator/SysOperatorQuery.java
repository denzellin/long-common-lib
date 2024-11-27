package com.isylph.operator.api.beans.operator;

import com.isylph.basis.base.BaseListQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author denzel.lin
 * @since 2019-08-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOperatorQuery extends BaseListQuery {

    private Long orgId;

    private String account;

    private String type;

    private String employeeId;

    private Long departmentId;

    private String name;

    private Integer gender;

    private Long userClass;

    private LocalDateTime birthday;

    private String nativePlace;

    private String ethnicGroup;

    private String college;

    private String education;

}
