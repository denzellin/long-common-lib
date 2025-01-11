package com.isylph.basis.security;


import com.isylph.basis.jwt.BaseJwtTokenService;
import com.isylph.basis.jwt.JwtTokenService;
import com.isylph.basis.jwt.entities.BaseJwtUser;
import com.isylph.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public interface BaseAuthenticationService<T extends BaseJwtUser, S extends BaseJwtTokenService<T>> {

    default UsernamePasswordAuthenticationToken createAuthenticationToken(
            Object principal, Object credentials, List<String> roles) {

        List<SimpleGrantedAuthority> auths = new ArrayList<>();
        for(String role: roles){
            auths.add(new SimpleGrantedAuthority(role));
        }

        return new UsernamePasswordAuthenticationToken(principal, credentials, auths);
    }

    default void checkAuthenticationAppSecret(String appId, String secretKey){

    }

    S getJwtService();
    boolean checkAppSecret(String appId, String secretKey);

    default UsernamePasswordAuthenticationToken getAuthentication(String token){

        if (StringUtils.isEmpty(token)){
            return null;
        }

        S jwtService = getJwtService();
        if (null == jwtService){
            return null;
        }

        T memberVo = getJwtService().getUser(token);
        if (memberVo == null){
            return null;
        }
        List<String> roles = getJwtService().getUserRole(token);
        return createAuthenticationToken(memberVo,memberVo, roles);
    }

    T createSessionUserContext();


    default UsernamePasswordAuthenticationToken getAuthentication(String appId, String secretKey) {

        T user = createSessionUserContext();

        user.setUsername("feignClient");

        // 当前对Feign客户端开放全部权限
        List<String> roles = List.of(BaseSecurityConfig.fullRightRole);
        return createAuthenticationToken(user,user, roles);
    }


    @SuppressWarnings("Duplicates")
    default Object checkAuthentication(HttpServletRequest request){

        S jwtService = getJwtService();
        if (null == jwtService){
            return null;
        }

        String tokenHeader = jwtService.getHeaderToken(request);
        String appId = jwtService.getHeaderAppId(request);
        String secretKey = jwtService.getHeaderSecret(request);

        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader != null ){
            UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(tokenHeader);
            if ( authenticationToken == null ) {
                return null;
            }

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            return authenticationToken.getCredentials();

        } else if (!StringUtils.isEmpty(appId) && !StringUtils.isEmpty(secretKey)){

            if (!checkAppSecret(appId, secretKey)){
                return null;
            }

            T user = createSessionUserContext();

            // 当前对Feign客户端开放全部权限
            List<String> roles = List.of(BaseSecurityConfig.fullRightRole);

            user.setUsername(jwtService.getHeaderUserName(request))
                    .setId(jwtService.getHeaderUserId(request))
                    .setUsername(jwtService.getHeaderUserName(request))
                    .setType(jwtService.getHeaderUserType(request))
                    .setName(jwtService.getHeaderName(request))
                    .setRoles(roles);

            try{
                UsernamePasswordAuthenticationToken authenticationToken =  createAuthenticationToken(user,user, roles);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                return authenticationToken.getCredentials();
            }catch (RuntimeException e){
                return null;
            }

        }

        return null;
    }
}
