package com.vivavot.console.api.beans.system.role;

import com.vivavot.basis.base.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleMemberSaveCmd extends BaseCmd {

    private Long roleId;

    private List<Long> userIds;
}
