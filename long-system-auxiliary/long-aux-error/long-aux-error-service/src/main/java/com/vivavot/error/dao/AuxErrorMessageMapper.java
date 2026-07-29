package com.vivavot.error.dao;


import com.vivavot.basis.persistence.dao.BaseMapperEx;
import com.vivavot.error.api.beans.AuxErrorMessageQuery;
import com.vivavot.error.model.AuxErrorMessagePO;

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
