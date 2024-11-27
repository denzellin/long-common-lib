package com.isylph.oss.domain.entity;


import com.isylph.oss.domain.types.LocationId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

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
public class OssFileLocation {

    private LocationId id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    /**
     * 模块
     */
    private String module;

    private String location;

    private String remark;

}
