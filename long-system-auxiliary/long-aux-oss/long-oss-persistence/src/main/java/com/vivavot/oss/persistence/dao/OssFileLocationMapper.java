package com.vivavot.oss.persistence.dao;


import com.vivavot.basis.persistence.dao.BaseMapperEx;
import com.vivavot.oss.api.entity.OssFileLocationQuery;
import com.vivavot.oss.persistence.model.OssFileLocationPO;

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
