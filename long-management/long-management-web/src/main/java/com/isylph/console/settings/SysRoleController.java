package com.isylph.console.settings;


import com.isylph.basis.beans.HttpRetData;
import com.isylph.basis.consts.BaseErrorConsts;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.console.api.beans.system.role.SysRoleQuery;
import com.isylph.console.api.beans.system.role.SysRoleSaveCmd;
import com.isylph.console.api.beans.system.role.SysRoleVO;
import com.isylph.console.api.beans.system.role.SysRoleUpdateCmd;
import com.isylph.console.settings.model.SysRolePO;
import com.isylph.console.settings.service.SysRoleService;
import com.isylph.controller.CrudController;
import com.isylph.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
@Slf4j
@Tag(name = "运维账户", description = "400")
@RestController
@RequestMapping("/console/role")
public class SysRoleController
        implements CrudController<
        SysRoleSaveCmd,
        SysRoleUpdateCmd,
        SysRoleQuery,
                SysRoleVO,
                SysRolePO> {

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public BaseService<SysRolePO> getService() {
        return sysRoleService;
    }

    @Operation(summary = "角色权限", method = "/**", operationId = "100")
    public HttpRetData demo() {
        return HttpRetData.success();
    }

    @Override
    public SysRolePO createModel() {
        return new SysRolePO();
    }

    @Override
    public SysRoleVO createViewObject() {
        return new SysRoleVO();
    }

    private Boolean isSysRole(SysRolePO po){
        if ( (po.getSys() != null && po.getSys() != 0)
        || (po.getMember() != null && po.getMember() != 0) ){
            return true;
        }

        return false;
    }

    @Override
    public HttpRetData updateByPrimaryKey(@RequestBody SysRoleUpdateCmd request) {
        SysRolePO po = sysRoleService.getById(request.getId());
        if (isSysRole(po)){
            log.info("can't edit the sys role: {}", po);
            throw new ReturnException(BaseErrorConsts.RET_BAD_PARAM);
        }

        SysRolePO newPo = new SysRolePO();
        newPo.setId(request.getId());
        newPo.setRemark(request.getRemark());
        newPo.setName(request.getName());
        newPo.setRole(request.getRole());
        newPo.setUrl(request.getUrl());
        sysRoleService.updateById(newPo);
        return HttpRetData.success();
    }

    @Override
    public HttpRetData deleteByPrimaryKey(Long id) {
        SysRolePO po = sysRoleService.getById(id);
        if (isSysRole(po)){
            log.info("can't edit the sys role: {}", po);
            throw new ReturnException(BaseErrorConsts.RET_BAD_PARAM);
        }

        sysRoleService.removeById(id);

        return HttpRetData.success();
    }

}

