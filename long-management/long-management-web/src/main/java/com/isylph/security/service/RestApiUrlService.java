package com.isylph.security.service;

import com.isylph.basis.consts.CommonConsts;
import com.isylph.console.api.beans.system.role.SysRoleVO;
import com.isylph.console.settings.model.SysFuncPO;
import com.isylph.console.settings.model.SysRolePO;
import com.isylph.console.settings.service.SysFuncService;
import com.isylph.console.settings.service.SysRoleFuncService;
import com.isylph.console.settings.service.SysRoleService;
import com.isylph.utils.StringUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @Author Denzel Lin
 * @Date 2026/3/11 16:28
 * @Version 1.0
 */
@Service
public class RestApiUrlService {

    public static class ApiPermission {

        String pattern;

        PathPattern pathPattern;

        Map<String, Set<String>> methodRoles;

    }

    private final static String HTTP_ALL = "HTTP_ALL";

    /**
     * Map<http Url, Map<method, role list></Url>
     *
     * http method: 不同HTTP请求防范分组，如果是所有方法，则存入
     */
    private final Map<String, Map<String, String>> urlAuthMaps = new HashMap<>();


    public static String ADMIN_ROLE;

    @Autowired
    private SysFuncService sysFuncService;

    @Autowired
    private SysRoleFuncService sysRoleFuncService;

    @Autowired
    private SysRoleService sysRoleService;


    private final PathPatternParser parser = new PathPatternParser();

    private volatile List<ApiPermission> permissions = new ArrayList<>();


    @PostConstruct
    public void init(){
        String rolePrefix = "ROLE_";

        SysRolePO roleAdmin =  sysRoleService.getById(CommonConsts.ROLE_ID_ADMIN);
        SysRolePO roleMember =  sysRoleService.getById(CommonConsts.ROLE_ID_MEMBER);

        ADMIN_ROLE = rolePrefix + roleAdmin.getRole();

        addUri( "/common/**",null,
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
                addUri(item.getUrl(), item.getMethod(), roles);
            }
        }


        List<ApiPermission> list = new ArrayList<>();

        for (Map.Entry<String, Map<String, String>> entry : urlAuthMaps.entrySet()) {

            ApiPermission permission = new ApiPermission();

            permission.pattern = entry.getKey();
            permission.pathPattern = parser.parse(entry.getKey());

            Map<String, Set<String>> methodRoles = new HashMap<>();

            for (Map.Entry<String, String> method : entry.getValue().entrySet()) {

                Set<String> roles = Arrays.stream(method.getValue().split(","))
                        .map(String::trim)
                        .collect(Collectors.toSet());

                methodRoles.put(method.getKey().toUpperCase(), roles);
            }

            permission.methodRoles = methodRoles;

            list.add(permission);
        }

        permissions = list;
    }

    public Set<String> getRoles(String method, String uri) {

        PathContainer path = PathContainer.parsePath(uri);

        for (ApiPermission permission : permissions) {

            if (!permission.pathPattern.matches(path)) {
                continue;
            }

            Set<String> roles = permission.methodRoles.get(method);

            if (roles == null) {
                roles = permission.methodRoles.get(HTTP_ALL);
            }

            if (roles != null) {
                return roles;
            }
        }

        return Set.of(ADMIN_ROLE);
    }

    public void addUri(String url, String method, List<String> roles){

        method = StringUtils.isEmpty(method)?HTTP_ALL:method;

        Map<String, String> urlMap = urlAuthMaps.computeIfAbsent(url, k -> new HashMap<>());
        urlMap.put(method.toUpperCase(), String.join(",", roles));
    }
}
