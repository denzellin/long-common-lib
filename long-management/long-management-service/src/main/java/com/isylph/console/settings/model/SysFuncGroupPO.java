package com.isylph.console.settings.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("sys_func_group")
public class SysFuncGroupPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分组编号
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 分组名称
     */
    private String name;


}
