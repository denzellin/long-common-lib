package com.isylph.department.persistence.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@TableName("department")
public class DepartmentPO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 菜单id
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    private String remark;


}
