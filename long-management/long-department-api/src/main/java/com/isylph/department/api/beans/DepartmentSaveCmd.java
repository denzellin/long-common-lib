package com.isylph.department.api.beans;

import com.isylph.basis.base.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentSaveCmd extends BaseCmd {

    private Long id;

    private Long fid;

    private Long orgId;

    private String title;

    private String remark;
}
