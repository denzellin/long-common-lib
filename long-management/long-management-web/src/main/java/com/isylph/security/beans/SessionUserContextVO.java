package com.isylph.security.beans;

import com.isylph.basis.jwt.beans.BaseJwtUser;
import com.isylph.console.api.beans.system.role.SysRoleVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/*
用于生成APP TOKEN的内容
* */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SessionUserContextVO extends BaseJwtUser {

    private String employeeId;

    private String mobile;

    private String permissionCode;

    private Long orgId;

    private List<SysRoleVO> roleList;
}
