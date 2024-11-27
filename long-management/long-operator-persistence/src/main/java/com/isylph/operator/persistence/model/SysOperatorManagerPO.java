package com.isylph.operator.persistence.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 部门主管配置
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_operator_manager")
public class SysOperatorManagerPO implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 账号ID
     */
    private Long operatorId;

    /**
     * 角色
     */
    private String role;

    /**
     * 权限优先级，数值倒序
     */
    private Integer priority;

    /**
     * 被委托人ID
     */
    private Long delegatedId;

    private String remark;


}
