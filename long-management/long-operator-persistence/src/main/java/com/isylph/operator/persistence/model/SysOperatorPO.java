package com.isylph.operator.persistence.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户 sys_user
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("sys_operator")
public class SysOperatorPO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 添加时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long orgId;
    /**
     * 账号
     */
    private String account;

    /**
     * 手机号码
     */
    private String mobile;

    private String email;

    /**
     * 头像
     */
    private String avatar;

    private String signature;

    private String title;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String salt;

    @TableField("`type`")
    private String type;

    /**
     * 用户状态 0 正常
     */
    private Integer status;

    private String remark;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String orgCode;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String permissionCode;

    /**
     * 删除标记
     */
    @TableField("is_deleted")
    private Integer deleted;


}
