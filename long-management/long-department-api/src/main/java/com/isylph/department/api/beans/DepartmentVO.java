package com.isylph.department.api.beans;

import com.isylph.basis.beans.Tree;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 部门组织结构, 部门主管等角色定义在manager表中
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DepartmentVO extends Tree {

    private Long orgId;

    private String title;

    private List<String> hierarchyTitles;

    private String remark;

}
