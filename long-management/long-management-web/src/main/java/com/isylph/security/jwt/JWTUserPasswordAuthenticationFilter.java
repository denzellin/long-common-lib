package com.isylph.security.jwt;

import com.isylph.basis.beans.HttpRetData;
import com.isylph.basis.consts.CommonConsts;
import com.isylph.security.beans.LoadUserVO;
import com.isylph.security.beans.SessionUserContextVO;
import com.isylph.utils.http.HttpRequestUtils;
import com.isylph.utils.json.JacksonUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
public class JWTUserPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTUserPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            String req = HttpRequestUtils.getRequestPostBody(request);
            LoadUserVO lgi = JacksonUtils.deserialize(req, LoadUserVO.class);

            //封装到token中提交
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    lgi, "");

            return authenticationManager.authenticate(authRequest);
        } catch (Exception e){
            e.printStackTrace();
            throw new BadCredentialsException("bad request");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities){
            roles.add(authority.getAuthority());
        }
        // 根据用户名，角色创建token
        String token = UserTokenAssemblerService.createToken((SessionUserContextVO)authResult.getPrincipal(), roles);


        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
        UserTokenAssemblerService.putHeaderToken(response, token);

        SecurityContextHolder.getContext().setAuthentication(authResult);
        response.setCharacterEncoding(CommonConsts.CHARSET_UTF8);
        response.setContentType("text/plain;charset=utf-8");
        response.getWriter().write(Objects.requireNonNull(JacksonUtils.serialize(HttpRetData.success())));
        //super.successfulAuthentication(request, response, chain, authResult);
    }

    // 这是验证失败时候调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(CommonConsts.CHARSET_UTF8);
        response.setContentType("text/plain;charset=utf-8");
        response.getWriter().write(Objects.requireNonNull(JacksonUtils.serialize(HttpRetData.error((long)HttpStatus.UNAUTHORIZED.value(), "账号或密码错误"))));
    }


}
