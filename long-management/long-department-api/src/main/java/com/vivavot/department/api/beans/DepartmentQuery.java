package com.vivavot.department.api.beans;

import com.vivavot.basis.base.BaseListQuery;
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
