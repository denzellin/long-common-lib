package com.isylph.error.service.impl;


import com.isylph.service.impl.BaseServiceImpl;
import com.isylph.error.api.beans.AuxErrorMessageQuery;
import com.isylph.error.dao.AuxErrorMessageMapper;
import com.isylph.error.model.AuxErrorMessagePO;
import com.isylph.error.service.AuxErrorMessageService;
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
