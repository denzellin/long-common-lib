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
 *
 * </p>
 *
 * @author denzel.lin
 * @since 2022-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("aux_export_cfg")
public class AuxExportCfgPO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String title;

    @TableField("`module`")
    private String module;

    /**
     * 模板文件
     */
    private String templateFile;

    private String sheetName;

    private Integer firstLine;

    private String sqlStatement;

    private String outputPath;

    private String remark;


}
