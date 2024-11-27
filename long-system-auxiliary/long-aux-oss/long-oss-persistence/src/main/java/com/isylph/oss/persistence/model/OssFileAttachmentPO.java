package com.isylph.oss.persistence.model;

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
 * 系统中静态资源文件的信息
 * </p>
 *
 * @author denzel.lin
 * @since 2021-12-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("oss_file_attachment")
public class OssFileAttachmentPO implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    /**
     * guid
     */
    private String guid;

    /**
     * 文件名
     */
    @TableField("`name`")
    private String name;

    /**
     * 本地保存相对路径
     */
    @TableField("`path`")
    private String path;

    private String remark;


}
