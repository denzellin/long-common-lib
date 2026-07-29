package com.vivavot.operator.persistence.dao;

import com.vivavot.basis.persistence.dao.BaseMapperEx;
import com.vivavot.operator.api.beans.operator.SysOperatorQuery;
import com.vivavot.operator.persistence.model.SysOperatorPO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author denzel.lin
 * @since 2019-08-13
 */
public interface SysOperatorMapper
        extends BaseMapperEx<SysOperatorPO, SysOperatorQuery> {

    SysOperatorPO selectByAccount(String account);

    SysOperatorPO getByAccount(String account);

    SysOperatorPO getByAccountId(Long accountId);

}
