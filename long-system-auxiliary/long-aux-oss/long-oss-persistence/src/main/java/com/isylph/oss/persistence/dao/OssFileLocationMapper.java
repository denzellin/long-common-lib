package com.isylph.oss.persistence.dao;


import com.isylph.basis.persistence.dao.BaseMapperEx;
import com.isylph.oss.api.entity.OssFileLocationQuery;
import com.isylph.oss.persistence.model.OssFileLocationPO;

import java.util.List;

/**
 * <p>
 * 系统中静态资源文件的保存路径 Mapper 接口
 * </p>
 *
 * @author denzel.lin
 * @since 2019-01-08
 */
public interface OssFileLocationMapper
        extends BaseMapperEx<OssFileLocationPO, OssFileLocationQuery> {


    OssFileLocationPO getByModule(String module);

}
