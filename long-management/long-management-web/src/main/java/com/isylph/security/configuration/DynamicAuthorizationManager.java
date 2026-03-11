package com.isylph.security.configuration;

import com.isylph.security.service.RestApiUrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Supplier;

/**
 * <p>
 *
 * </p>
 *
 * @Author Denzel Lin
 * @Date 2026/3/11 16:26
 * @Version 1.0
 */
@Component
public class DynamicAuthorizationManager
        implements AuthorizationManager<RequestAuthorizationContext> {

    private final RestApiUrlService restApiUrlService;

    public DynamicAuthorizationManager(RestApiUrlService restApiUrlService) {
        this.restApiUrlService = restApiUrlService;
    }

    @Override
    public AuthorizationDecision check(
            Supplier<Authentication> authentication,
            RequestAuthorizationContext context) {

        Authentication auth = authentication.get();

        if (auth == null || !auth.isAuthenticated()) {
            return new AuthorizationDecision(false);
        }

        HttpServletRequest request = context.getRequest();

        String method = request.getMethod();
        String uri = request.getServletPath();

        Set<String> roles = restApiUrlService.getRoles(method, uri);

        for (GrantedAuthority authority : auth.getAuthorities())
        {

            if (roles.contains(authority.getAuthority())) {

                return new AuthorizationDecision(true);
            }
        }

        return new AuthorizationDecision(false);
    }

    @Override
    public AuthorizationResult authorize(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        return AuthorizationManager.super.authorize(authentication, object);
    }

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }
}
