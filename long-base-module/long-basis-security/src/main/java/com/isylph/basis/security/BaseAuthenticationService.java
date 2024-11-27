package com.isylph.basis.security;


import com.isylph.basis.jwt.BaseJwtTokenService;
import com.isylph.basis.jwt.beans.BaseJwtUser;
import com.isylph.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
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

        T memberVo = createSessionUserContext();

        memberVo
                .setUsername("feignClient")
                .setType(10);

        // 当前对Feign客户端开放全部权限
        List<String> roles = List.of(BaseSecurityConfig.fullRightRole);
        return createAuthenticationToken(memberVo,memberVo, roles);
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

        } /*else if (!StringUtils.isEmpty(appId) && !StringUtils.isEmpty(secretKey)){

            try{
                UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(appId, secretKey);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                return authenticationToken.getCredentials();
            }catch (RuntimeException e){
                return null;
            }

        } */

        return null;
    }
}
