package com.isylph.security.configuration;


import com.isylph.basis.security.BaseSecurityConfig;
import com.isylph.security.jwt.JWTAuthenticationProvider;
import com.isylph.security.jwt.JWTAuthenticationFilter;
import com.isylph.security.jwt.JWTUserPasswordAuthenticationFilter;
import com.isylph.security.jwt.UserTokenAssemblerService;
import com.isylph.security.service.LongUserDetailsService;
import com.isylph.security.service.RestApiUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpBasicSpec;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements BaseSecurityConfig {

    @Autowired
    private UserTokenAssemblerService userTokenAssemblerService;

    @Autowired
    private LongUserDetailsService longUserDetailsService;

    @Autowired
    private RestApiUrlService restApiUrlService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(register -> register.anyRequest().access((authentication, object) -> {
                    //获取当前请求的 URL 地址
                    String requestURI = object.getRequest().getRequestURI();
                    AtomicBoolean ignored = new AtomicBoolean(false);
                    if (authentication.get() instanceof AnonymousAuthenticationToken) {
                        ignoreUrls.forEach(url-> {
                            if (antPathMatcher.match(url, requestURI)){
                                ignored.set(true);
                            }
                        });
                        if (ignored.get() ) {
                            return new AuthorizationDecision(true);
                        }
                        return new AuthorizationDecision(false);
                    }

                    String method = object.getRequest().getMethod();
                    if (antPathMatcher.match("/common/**", requestURI) || antPathMatcher.match("/tool/**", requestURI) ){
                        return new AuthorizationDecision(true);
                    }
                    List<ConfigAttribute> cs = restApiUrlService.getAuth(method, requestURI);
                    Collection<? extends GrantedAuthority> authorities = authentication.get().getAuthorities();
                    for (GrantedAuthority authority : authorities) {
                        for (ConfigAttribute role : cs) {
                            if (authority.getAuthority().equals(role.getAttribute())) {
                                //说明当前登录用户具备当前请求所需要的角色
                                return new AuthorizationDecision(true);
                            }
                        }
                    }
                    return new AuthorizationDecision(false);
                }))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JWTAuthenticationFilter(userTokenAssemblerService), UsernamePasswordAuthenticationFilter.class)
                .addFilter(new JWTUserPasswordAuthenticationFilter(authenticationManager))
                .exceptionHandling(Customizer.withDefaults())
                .httpBasic(HttpBasicConfigurer::disable)
                .logout(logout->{
                    logout.logoutSuccessHandler((request, response, authentication) -> {
                        SecurityContextHolder.clearContext();
                    });
                });

        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new JWTAuthenticationProvider(longUserDetailsService);
    }


    @Bean
    protected LongUserDetailsService userAccountService() {
        return new LongUserDetailsService();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        return baseCorsConfigurationSource();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    { return new BCryptPasswordEncoder(); }
}
