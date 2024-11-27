package com.isylph.aux.excel.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文件导出excel配置
 * </p>
 *
 * @author denzel.lin
 * @since 2022-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("aux_export_column_cfg")
public class AuxExportColumnCfgPO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 模块
     */
    @TableField("`module`")
    private String module;

    /**
     * sql语句查询的结果列名
     */
    @TableField("`key`")
    private String key;

    /**
     * 结果集列对应excel的列
     */
    @TableField("`column`")
    private Integer column;

    private String remark;


}
