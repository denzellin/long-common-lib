package com.isylph.console.common;


import com.isylph.basis.beans.HttpRetData;
import com.isylph.basis.beans.Tree;
import com.isylph.console.api.beans.common.GetPermissionVO;
import com.isylph.console.api.beans.common.PermissionVO;
import com.isylph.console.api.beans.system.func.SysFuncVO;
import com.isylph.console.api.beans.system.menu.SysMenuVO;
import com.isylph.console.api.beans.system.role.SysRoleVO;
import com.isylph.console.settings.service.SysRoleFuncService;
import com.isylph.console.settings.service.SysRoleMenuService;
import com.isylph.console.settings.service.SysRoleUserService;
import com.isylph.operator.api.beans.operator.SysOperatorUpdateCmd;
import com.isylph.operator.api.beans.operator.SysOperatorVO;
import com.isylph.operator.application.OperatorApplicationService;
import com.isylph.operator.domain.types.AccountId;
import com.isylph.security.base.SecurityBaseController;
import com.isylph.utils.StringUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Tag(name = "通用功能管理", description = "")
@RestController
@RequestMapping("/common")
public class CommonController extends SecurityBaseController {

    @Autowired
    private OperatorApplicationService operatorApplicationService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysRoleFuncService sysRoleFuncService;

    @Autowired
    private SysRoleUserService sysRoleUserService;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private List<PermissionVO> setSuperAdminPermission(GetPermissionVO req){
        List<PermissionVO> ps = new ArrayList<>();
        for(PermissionVO url: req.getUrls()){
            PermissionVO p = new PermissionVO().setPermission(true).setUrl(url.getUrl()).setMethod(url.getMethod());
            ps.add(p);
        }
        return ps;
    }

    @Operation(summary = "前端错误日志")
    @PostMapping(value = "/log")
    public HttpRetData log(String logInfo) {
        log.info("Front end log: {}", logInfo);
        return HttpRetData.success();
    }

    @Operation(summary = "获取当前用户菜单")
    @GetMapping(value = "/menu")
    public HttpRetData<List<SysMenuVO>> getMenus() {
        List<SysMenuVO> list = sysRoleMenuService.listRolesMenus(getCurrentAccountRoles());
        return HttpRetData.success(Tree.assembleTree(list));
    }

    @Operation(summary = "判断功能权限")
    @PostMapping(value = "/permission")
    public HttpRetData<List<PermissionVO>> pageAuth(@RequestBody GetPermissionVO req) {

        List<PermissionVO> permissions = req.getUrls();
        if (CollectionUtils.isEmpty(permissions)){
            return HttpRetData.success();
        }
        if (isSuperAadmin()){
            return HttpRetData.success(setSuperAdminPermission(req));
        }

        for (PermissionVO p: permissions){
            p.setPermission(false);
        }

        List<SysFuncVO> funcs = sysRoleFuncService.listRolesFuncs(getCurrentAccountRoles());

        int cnt = 0;
        int size = req.getUrls().size();
        for(SysFuncVO item: funcs){
            if(cnt >= size){
                break;
            }
            for(PermissionVO url: permissions){
                if(url.getPermission() == true){
                    continue;
                }

                if(antPathMatcher.match(item.getUrl(),url.getUrl())
                        && (StringUtils.isEmpty(item.getMethod()) || (0 == url.getMethod().compareToIgnoreCase(item.getMethod())))){
                    url.setPermission(true);
                    cnt++;
                    continue;
                }

                if((0 == item.getUrl().compareToIgnoreCase(url.getUrl() + "/*"))
                        && (StringUtils.isEmpty(item.getMethod()) || (0 == url.getMethod().compareToIgnoreCase(item.getMethod())))){
                    url.setPermission(true);
                    cnt++;
                }
            }
        }

        return HttpRetData.success(permissions);
    }

    @Operation(summary = "当前登录用户信息")
    @GetMapping(value = "/profile/r")
    public HttpRetData<SysOperatorVO> userDetail() {

        SysOperatorVO vo = operatorApplicationService.getOperator(getCurrentAccountId());
        return HttpRetData.success(vo);
    }

    @Operation(summary = "当前登录用户的默认路由")
    @GetMapping(value = "/profile/default-route")
    public HttpRetData<List<String>> getUserDefaultRoute() {
        List<SysRoleVO> roleList = sysRoleUserService.getUserRoles(getCurrentAccountId());
        List<String> urls = new ArrayList<>();
        if(!CollectionUtils.isEmpty(roleList)){
            roleList.forEach(role -> urls.add(role.getUrl()));
        }
        return HttpRetData.success(urls);
    }

    @Operation(description = "修改当前登录用户信息")
    @PostMapping(value = "/profile/u")
    public HttpRetData updateUser(@RequestBody SysOperatorUpdateCmd saveVo) {

        saveVo.setId(getCurrentAccountId());
        operatorApplicationService.updateOperator(saveVo);
        return HttpRetData.success();
    }

    @Operation(summary = "修改当前登录用户密码")
    @PostMapping(value = "/password/u")
    public HttpRetData updateUserPwd(String old, String newz) {

        SysOperatorVO vo = operatorApplicationService.login(getCurrentAccount(), old);

        operatorApplicationService.updatePassword(vo.getId(), newz);

        return HttpRetData.success();
    }

}
