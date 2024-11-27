package com.isylph.security.jwt;

import com.isylph.security.beans.LoadUserVO;
import com.isylph.security.beans.SessionUserContextVO;
import com.isylph.security.service.LongUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * <p>
 *  用户账号、密码验证，返回用户信息和角色
 * </p>
 *
 * @Author SYLPH Technologies Co., Ltd
 * @Date 2024/3/17 17:09
 * @Version 1.0
 */

public class JWTAuthenticationProvider implements AuthenticationProvider {

    private LongUserDetailsService userService;


    public JWTAuthenticationProvider(LongUserDetailsService userService) {
        this.userService = userService;
    }

    /**
     * 自定义用户的验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoadUserVO userVo = (LoadUserVO)authentication.getPrincipal();

        SessionUserContextVO user;
        try {
            user = userService.loadUser(userVo);

        }catch (Exception e){
            throw new DisabledException("系统忙");
        }

        if (null == user) {
            throw new DisabledException("账号或密码错误");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, "", authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}
