package com.isylph.operator.api.beans.grouplist;

import com.isylph.basis.base.BaseCmd;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GroupUpdateCmd extends BaseCmd {

    private Long id;

    private String groupName;

    private Long roleId;

    private String remark;
}
