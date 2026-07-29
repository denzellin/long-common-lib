package com.vivavot.console.settings.service.impl;


import com.vivavot.console.api.beans.system.func.SysFuncGroupQuery;
import com.vivavot.console.settings.dao.SysFuncGroupMapper;
import com.vivavot.console.settings.model.SysFuncGroupPO;
import com.vivavot.console.settings.service.SysFuncGroupService;
import com.vivavot.service.impl.BaseServiceImpl;
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
