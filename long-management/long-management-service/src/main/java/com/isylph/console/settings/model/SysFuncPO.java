package com.isylph.console.settings.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("sys_func")
public class SysFuncPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 功能id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    /**
     * 功能名称
     */
    private String name;

    /**
     * 0禁用 1启用
     */
    private Integer active;

    /**
     * 分组编号
     */
    private Long groupId;

    private String method;

    /**
     * http的url
     */
    private String url;


}
