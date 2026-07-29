package com.vivavot.department.api.beans;

import com.vivavot.basis.base.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentSaveCmd extends BaseCmd {

    private Long id;

    private Long fid;

    private Long orgId;

    private String title;

    private String remark;
}
