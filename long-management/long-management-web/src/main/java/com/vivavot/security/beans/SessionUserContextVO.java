package com.vivavot.security.beans;

import com.vivavot.basis.jwt.entities.BaseJwtUser;
import com.vivavot.console.api.beans.system.role.SysRoleVO;
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
