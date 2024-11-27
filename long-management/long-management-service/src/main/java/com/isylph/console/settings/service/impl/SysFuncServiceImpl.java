package com.isylph.console.settings.service.impl;


import com.isylph.console.api.beans.system.func.SysFuncQuery;
import com.isylph.console.settings.dao.SysFuncMapper;
import com.isylph.console.settings.model.SysFuncPO;
import com.isylph.console.settings.service.SysFuncService;
import com.isylph.service.impl.BaseServiceImpl;
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
