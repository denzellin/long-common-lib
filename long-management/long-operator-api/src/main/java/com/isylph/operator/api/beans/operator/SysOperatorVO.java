package com.isylph.operator.api.beans.operator;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isylph.basis.beans.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户 sys_user
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysOperatorVO extends BaseVO {

    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long orgId;
    private String orgName;

    private String account;

    private String mobile;

    private String email;

    private String avatar;

    private String signature;

    private String title;

    private String name;

    private Integer status;

    @JsonIgnore
    private String orgCode;

    private String type;

    @JsonIgnore
    private String permissionCode;

    private String remark;
}
