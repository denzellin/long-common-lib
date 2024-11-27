package com.isylph.oss.persistence.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 系统中静态资源文件的保存路径
 * </p>
 *
 * @author denzel.lin
 * @since 2019-01-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oss_file_location")
public class OssFileLocationPO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    /**
     * 模块
     */
    private String module;

    private String location;

    private String remark;

}
