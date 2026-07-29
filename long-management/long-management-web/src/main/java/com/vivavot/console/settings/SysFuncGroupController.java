package com.vivavot.console.settings;


import com.vivavot.console.api.beans.system.func.SysFuncGroupQuery;
import com.vivavot.console.api.beans.system.func.SysFuncGroupSaveCmd;
import com.vivavot.console.api.beans.system.func.SysFuncGroupVO;
import com.vivavot.console.api.beans.system.func.SysFuncGroupUpdateCmd;
import com.vivavot.console.settings.model.SysFuncGroupPO;
import com.vivavot.console.settings.service.SysFuncGroupService;
import com.vivavot.controller.CrudController;
import com.vivavot.service.BaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
@Tag(name = "系统功能", description = "100")
@RestController
@RequestMapping("console/function/group")
public class SysFuncGroupController
        implements CrudController<
        SysFuncGroupSaveCmd,
        SysFuncGroupUpdateCmd,
        SysFuncGroupQuery,
                SysFuncGroupVO,
                SysFuncGroupPO> {

    @Autowired
    private SysFuncGroupService sysFuncGroupService;

    @Override
    public BaseService<SysFuncGroupPO> getService() {
        return sysFuncGroupService;
    }

    @Override
    public SysFuncGroupPO createModel() {
        return new SysFuncGroupPO();
    }

    @Override
    public SysFuncGroupVO createViewObject() {
        return new SysFuncGroupVO();
    }
}

