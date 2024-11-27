package com.isylph.security.jwt;

import com.isylph.basis.security.BaseAuthenticationService;
import com.isylph.security.beans.SessionUserContextVO;
import com.isylph.security.http.BodyReaderHttpServletRequestWrapper;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class JWTAuthenticationFilter
        extends OncePerRequestFilter
        implements BaseAuthenticationService<SessionUserContextVO, UserTokenAssemblerService> {


    private final UserTokenAssemblerService jwtTokenService;

    public JWTAuthenticationFilter(UserTokenAssemblerService jwtService) {
        jwtTokenService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String uri = request.getRequestURI();
        if (Objects.equals(uri, "/login")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            /*
             尝试解析token，如果成功则返回用户实例, 并将用户信息放入SecurityContextHolder
            */
            SessionUserContextVO user = (SessionUserContextVO) checkAuthentication(request);
            if(user != null) {
                // 增加系统日志
                HttpServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request,user);
                chain.doFilter(requestWrapper, response);
                return;
            }
        } catch (SignatureException e) {
            log.info("Invalid JWT signature: {}", e.getMessage());
        }

        log.debug("no token found....");
        chain.doFilter(request, response);

    }

    @Override
    public UserTokenAssemblerService getJwtService() {
        return jwtTokenService;
    }

    @Override
    public SessionUserContextVO createSessionUserContext() {
        return new SessionUserContextVO();
    }
}
