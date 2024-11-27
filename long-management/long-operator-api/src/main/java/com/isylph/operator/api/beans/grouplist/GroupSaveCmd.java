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
public class GroupSaveCmd extends BaseCmd {

    private String groupName;

    private String remark;

    private Long roleId;

}
