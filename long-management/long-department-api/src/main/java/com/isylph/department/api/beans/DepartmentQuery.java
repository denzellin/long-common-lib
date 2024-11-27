package com.isylph.department.api.beans;

import com.isylph.basis.base.BaseListQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DepartmentQuery extends BaseListQuery {
    private Long fid;
    private Long orgId;
    private String name;

    private String code;

    public DepartmentQuery(Long fid, String name) {
        this.fid = fid;
        this.name = name;
    }
}
