package com.vivavot.console.settings.service.impl;


import com.vivavot.console.api.beans.system.func.SysFuncQuery;
import com.vivavot.console.settings.dao.SysFuncMapper;
import com.vivavot.console.settings.model.SysFuncPO;
import com.vivavot.console.settings.service.SysFuncService;
import com.vivavot.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
@Service
public class SysFuncServiceImpl
        extends BaseServiceImpl<
                SysFuncMapper,
                SysFuncPO,
        SysFuncQuery>
        implements SysFuncService {

    @Override
    public List<SysFuncPO> listAll() {
        return list();
    }
}
