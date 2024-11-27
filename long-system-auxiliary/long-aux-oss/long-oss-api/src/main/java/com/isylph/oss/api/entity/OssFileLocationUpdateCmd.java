package com.isylph.oss.api.entity;

import com.isylph.basis.base.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统中静态资源文件的保存路径
 * </p>
 *
 * @author denzel.lin
 * @since 2019-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OssFileLocationUpdateCmd extends BaseCmd {

    private Long id;

    private String module;

    private String location;

    private String remark;


}
