package com.vivavot.error.service;


import com.vivavot.error.model.AuxErrorMessagePO;
import com.vivavot.service.BaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-19
 */
public interface AuxErrorMessageService extends BaseService<AuxErrorMessagePO> {
    void saveError(AuxErrorMessagePO i);
}
