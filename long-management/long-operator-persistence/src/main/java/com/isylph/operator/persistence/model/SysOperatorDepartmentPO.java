package com.isylph.operator.persistence.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 部门成员
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_operator_department")
public class SysOperatorDepartmentPO implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 成员账号ID
     */
    private Long memberId;

    /**
     * 权限优先级，数值倒序
     */
    private Integer priority;

    private String remark;


}
