package com.isylph.security.service;


import com.isylph.console.api.beans.system.role.SysRoleVO;
import com.isylph.console.settings.service.SysRoleUserService;
import com.isylph.operator.api.beans.operator.SysOperatorVO;
import com.isylph.operator.application.OperatorApplicationService;
import com.isylph.security.beans.LoadUserVO;
import com.isylph.security.beans.SessionUserContextVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class LongUserDetailsService implements UserDetailsService {

    @Value("${services.app-id}")
    private String serviceAppId;

    @Value("${services.app-secret}")
    private String serviceSecret;

    @Autowired
    private OperatorApplicationService operatorApplicationService;

    @Autowired
    private SysRoleUserService sysRoleUserService;

    public LongUserDetailsService() {
    }

    public Boolean checkAppSecret(String appId, String appSecret) {
        return Objects.equals(appId, serviceAppId) && Objects.equals(appSecret, serviceSecret);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public static SessionUserContextVO getUserContext(SysOperatorVO user, List<SysRoleVO> roleList) {
        SessionUserContextVO userContext = new SessionUserContextVO();

        List<String> roles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roleList)){
            roles = roleList.stream().map(r -> "ROLE_"+r.getRole()).collect(Collectors.toList());
            userContext.setRoles(roles);
        }

        userContext
                .setPermissionCode(user.getPermissionCode())
                .setOrgId(user.getOrgId())
                .setRoleList(roleList)
                .setRoles(roles)
                .setUsername(user.getAccount())
                .setName(user.getName())
                .setId(user.getId());

        return userContext;
    }

    public SessionUserContextVO loadUser(LoadUserVO vo){

        SysOperatorVO user = operatorApplicationService.login(vo.getUsername(), vo.getPassword());
        if (null == user){
            return null;
        }
        List<SysRoleVO> roleList = sysRoleUserService.getUserRoles(user.getId());
        return getUserContext(user, roleList);
    }
}
