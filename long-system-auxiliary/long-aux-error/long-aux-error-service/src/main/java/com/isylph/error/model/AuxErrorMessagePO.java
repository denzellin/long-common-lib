package com.isylph.error.model;

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
 * @since 2021-08-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("aux_error_code")
public class AuxErrorMessagePO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 错误码值
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    private String module;

    /**
     * 模块
     */
    @TableField("`key`")
    private String key;

    /**
     * 错误描述
     */
    private String message;
    private String remark;


}
