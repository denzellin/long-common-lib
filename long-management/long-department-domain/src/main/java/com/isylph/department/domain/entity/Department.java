package com.isylph.department.domain.entity;


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
public class Department {

    private Long id;

    /**
     * 父菜单id
     */
    private Long fid;

    /**
     * 菜单树
     */
    private String code;

    /**
     * 菜单标题
     */
    private String title;

    private List<String> hierarchyTitles;

    private String remark;
}
