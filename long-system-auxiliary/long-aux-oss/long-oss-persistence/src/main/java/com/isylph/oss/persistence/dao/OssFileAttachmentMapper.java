package com.isylph.oss.persistence.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isylph.oss.persistence.model.OssFileAttachmentPO;

/**
 * <p>
 * 系统中静态资源文件的信息 Mapper 接口
 * </p>
 *
 * @author denzel.lin
 * @since 2021-12-28
 */
public interface OssFileAttachmentMapper extends BaseMapper<OssFileAttachmentPO> {

    OssFileAttachmentPO getByGuid(String guid);

}
