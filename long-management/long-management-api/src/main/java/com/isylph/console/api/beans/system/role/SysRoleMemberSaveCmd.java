package com.isylph.console.api.beans.system.role;

import com.isylph.basis.base.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleMemberSaveCmd extends BaseCmd {

    private Long roleId;

    private List<Long> userIds;
}
