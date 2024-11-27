package com.isylph.console.settings.service.impl;


import com.isylph.console.api.beans.system.func.SysFuncGroupQuery;
import com.isylph.console.settings.dao.SysFuncGroupMapper;
import com.isylph.console.settings.model.SysFuncGroupPO;
import com.isylph.console.settings.service.SysFuncGroupService;
import com.isylph.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
@Service
public class SysFuncGroupServiceImpl
        extends BaseServiceImpl<
                SysFuncGroupMapper,
                SysFuncGroupPO,
        SysFuncGroupQuery>
        implements SysFuncGroupService {

}
