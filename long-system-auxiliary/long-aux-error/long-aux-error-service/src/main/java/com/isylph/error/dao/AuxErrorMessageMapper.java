package com.isylph.error.dao;


import com.isylph.basis.persistence.dao.BaseMapperEx;
import com.isylph.error.api.beans.AuxErrorMessageQuery;
import com.isylph.error.model.AuxErrorMessagePO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-19
 */
public interface AuxErrorMessageMapper extends BaseMapperEx<AuxErrorMessagePO, AuxErrorMessageQuery> {
    void saveError(AuxErrorMessagePO i);
}
