package com.isylph.console.settings.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author denzel.lin
 * @since 2019-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_menu")
public class SysMenuPO implements Serializable {

    private static final long serialVersionUID = 1L;

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

    private String name;

    private String path;

    /**
     * 图标
     */
    private String icon;

    /**
     * 链接路径
     */
    private String component;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 0禁用 1启用
     */
    private Integer active;

    /**
     * 0,1
     */
    private Integer cache;

    /**
     * 0,1
     */
    private Integer hideInMenu;
    /**
     * 0,1
     */
    private Integer hideInBread;


    /**
     * 系统管理admin的菜单
     */
    @TableField(value = "is_sys")
    private Integer sys;

    /**
     * 普通操作员菜单
     */
    @TableField(value = "is_user")
    private Integer user;
}
