package com.isylph.oss.domain.entity;


import com.isylph.oss.domain.types.AttachmentId;
import com.isylph.oss.domain.types.FileGuid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class OssFileAttachment{

    private AttachmentId id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    /**
     * guid
     */
    private FileGuid guid;

    /**
     * 文件名
     */
    private String name;

    /**
     * 本地保存相对路径
     */
    private String path;

    private String remark;


}
