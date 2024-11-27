package com.isylph.operator.api.beans.operator;

import com.isylph.basis.base.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class SysOperatorSaveCmd extends BaseCmd {

    private Long orgId;

    private String account;

    private String mobile;

    private String email;

    private String type;

    private String avatar;

    private String signature;

    private String title;

    private String name;

    private Integer status;

    private String remark;

    private String password;
}
