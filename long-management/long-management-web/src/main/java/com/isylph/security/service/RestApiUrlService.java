package com.isylph.security.service;


import com.isylph.basis.consts.CommonConsts;
import com.isylph.console.api.beans.system.role.SysRoleVO;
import com.isylph.console.settings.model.SysFuncPO;
import com.isylph.console.settings.model.SysRolePO;
import com.isylph.console.settings.service.SysFuncService;
import com.isylph.console.settings.service.SysRoleFuncService;
import com.isylph.console.settings.service.SysRoleService;
import com.isylph.security.beans.UrlAuthCollection;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RestApiUrlService {

    UrlAuthCollection urlAuths;

    public static String ADMIN_ROLE;

    @Autowired
    private SysFuncService sysFuncService;

    @Autowired
    private SysRoleFuncService sysRoleFuncService;

    @Autowired
    private SysRoleService sysRoleService;

    @PostConstruct
    public void init(){
        urlAuths = new UrlAuthCollection();
        String rolePrefix = "ROLE_";

        SysRolePO roleAdmin =  sysRoleService.getById(CommonConsts.ROLE_ID_ADMIN);
        SysRolePO roleMember =  sysRoleService.getById(CommonConsts.ROLE_ID_MEMBER);

        RestApiUrlService.ADMIN_ROLE = rolePrefix + roleAdmin.getRole();

        urlAuths.add( "/common/**",null,
                Arrays.asList(rolePrefix+roleAdmin.getRole(), rolePrefix+roleMember.getRole()));

        List<SysFuncPO> funcs = sysFuncService.listAll();
        if (CollectionUtils.isEmpty(funcs)){
            return;
        }

        for(SysFuncPO item: funcs){
            List<String> roles = new ArrayList<>();
            List<SysRoleVO> roleVos = sysRoleFuncService.listRolesByFunc(item.getId());

            if (!CollectionUtils.isEmpty(roleVos)){
                for(SysRoleVO vo: roleVos){
                    roles.add(rolePrefix+vo.getRole());
                }

                roles.add(rolePrefix+roleAdmin.getRole());
                urlAuths.add(item.getUrl(), item.getMethod(), roles);
            }

        }
    }

    public List<ConfigAttribute> getAuth(String httpMethod, String url){
        return urlAuths.getAuth(httpMethod, url);
    }

}
