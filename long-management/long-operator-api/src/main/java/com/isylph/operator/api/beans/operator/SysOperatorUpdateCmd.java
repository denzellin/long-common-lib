package com.isylph.operator.api.beans.operator;

import com.isylph.basis.base.BaseCmd;
import com.isylph.operator.api.beans.department.OperatorDepartmentVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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
public class SysOperatorUpdateCmd extends BaseCmd {

    private Long id;


    private Long orgId;

    @Schema(name = "账号")
    private String account;

    @Schema(name = "手机号码")
    private String mobile;

    private String email;

    private String type;

    @Schema(name = "头像")
    private String avatar;

    private String signature;

    private String title;

    @Schema(name = "姓名")
    private String name;

    private Integer status;

    private String remark;

    List<OperatorDepartmentVO> departments;
}
