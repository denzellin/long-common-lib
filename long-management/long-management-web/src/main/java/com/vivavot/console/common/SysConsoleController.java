package com.vivavot.console.common;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vivavot.basis.beans.HttpRetData;
import com.vivavot.basis.beans.Tree;
import com.vivavot.console.api.beans.system.func.SysFuncVO;
import com.vivavot.console.api.beans.system.menu.SysMenuQuery;
import com.vivavot.console.api.beans.system.menu.SysMenuVO;
import com.vivavot.console.api.beans.system.role.SysRoleFuncSaveCmd;
import com.vivavot.console.api.beans.system.role.SysRoleMenuSaveCmd;
import com.vivavot.console.api.beans.system.role.SysRoleMemberSaveCmd;
import com.vivavot.console.settings.model.SysMenuPO;
import com.vivavot.console.settings.service.SysMenuService;
import com.vivavot.console.settings.service.SysRoleFuncService;
import com.vivavot.console.settings.service.SysRoleMenuService;
import com.vivavot.console.settings.service.SysRoleUserService;
import com.vivavot.operator.api.beans.operator.SysOperatorVO;
import com.vivavot.security.service.RestApiUrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
@Slf4j
@Tag(name = "系统功能", description = "100")
@RestController
@RequestMapping("/console/role")
public class SysConsoleController {

    @Autowired
    private SysRoleUserService sysRoleUserService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleFuncService sysRoleFuncService;

    @Autowired
    private RestApiUrlService restApiUrlService;

    @Operation
    @PostMapping(value = "/member/r")
    public HttpRetData<List<SysOperatorVO>> listRoleMembers(@RequestParam(value = "roleId") Long roleId) {

        List<SysOperatorVO> roleUserList = sysRoleUserService.listUserByRoleId(roleId);
        if (!CollectionUtils.isEmpty(roleUserList)) {
            return HttpRetData.success(roleUserList, roleUserList.size());
        }

        return HttpRetData.success(null, 0);
    }


    @Operation(summary = "保存角色用户成员")
    @PostMapping(value = "/member/c")
    public HttpRetData saveRoleMember(@RequestBody SysRoleMemberSaveCmd req){

        Long roleId = req.getRoleId();
        List<Long> userIds = req.getUserIds();
        sysRoleUserService.saveRoleMember(roleId, userIds);
        return HttpRetData.success();
    }


    @Operation(summary = "保存角色菜单")
    @PostMapping(value = "/menu/c")
    public HttpRetData saveRoleMateMenu(@RequestBody SysRoleMenuSaveCmd req){

        sysRoleMenuService.saveRoleMenu(req.getRoleId(), req.getMenuIds());
        return HttpRetData.success();
    }

    @Operation(summary = "查找角色菜单组")
    @PostMapping(value = "/menu/r")
    public HttpRetData<List<SysMenuVO>> listRoleMenus(@RequestParam(value = "roleId", required = false) Long roleId) {

        List<SysMenuVO> menus;
        if (null == roleId){
            SysMenuQuery query = new SysMenuQuery();
            Page<SysMenuPO> page = sysMenuService.selectViewListPage(query);
            List<SysMenuVO> vos = new ArrayList<>();
            for (SysMenuPO p: page.getRecords()){
                SysMenuVO v = new SysMenuVO();
                vos.add(v);
            }
            menus = Tree.assembleTree(vos);
        }else{
            menus = Tree.assembleTree(sysRoleMenuService.listRoleMenu(roleId));
        }

        return HttpRetData.success(menus);
    }

    @Operation(summary = "保存角色功能")
    @PostMapping(value = "/func/c")
    public HttpRetData saveRoleFunc(@RequestBody SysRoleFuncSaveCmd req) {

        sysRoleFuncService.saveRoleFunc(req.getRoleId(), req.getFuncIds());
        restApiUrlService.init();
        return HttpRetData.success();
    }

    @Operation(summary = "查找角色功能组")
    @PostMapping(value = "/func/r")
    public HttpRetData<List<SysFuncVO>> listRoleFunc(@RequestParam(value = "roleId", required = false) Long roleId) {

        if(null == roleId){
            return HttpRetData.success(sysRoleFuncService.listAllFunc());
        }else{
            return HttpRetData.success(sysRoleFuncService.listRoleFunc(roleId));
        }
    }

}

