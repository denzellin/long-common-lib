package com.isylph.console.settings.model;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2019-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role")
public class SysRolePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色
     */
    private String role;


    /**
     * 角色默认页面路由
     */
    private String url;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否系统(0系统，1自定义)
     */
    @TableField(value = "`sys`")
    private Integer sys;

    /**
     * 是否配置成员(0不配置，1配置)
     */
    @TableField(value = "`member`")
    private Integer member;


}
