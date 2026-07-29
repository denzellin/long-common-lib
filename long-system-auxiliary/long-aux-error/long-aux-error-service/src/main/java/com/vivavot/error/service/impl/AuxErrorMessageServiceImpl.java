package com.vivavot.error.service.impl;


import com.vivavot.service.impl.BaseServiceImpl;
import com.vivavot.error.api.beans.AuxErrorMessageQuery;
import com.vivavot.error.dao.AuxErrorMessageMapper;
import com.vivavot.error.model.AuxErrorMessagePO;
import com.vivavot.error.service.AuxErrorMessageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author denzel.lin
 * @since 2021-08-19
 */
@Service
public class AuxErrorMessageServiceImpl
        extends BaseServiceImpl<AuxErrorMessageMapper, AuxErrorMessagePO, AuxErrorMessageQuery>
        implements AuxErrorMessageService {

    @Override
    public void saveError(AuxErrorMessagePO i) {
        baseMapper.saveError(i);
        return;
    }

}
