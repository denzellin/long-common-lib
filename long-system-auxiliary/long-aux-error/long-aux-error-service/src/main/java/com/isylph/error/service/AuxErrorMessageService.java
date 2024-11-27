package com.isylph.error.service;


import com.isylph.error.model.AuxErrorMessagePO;
import com.isylph.service.BaseService;

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
