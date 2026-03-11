package com.isylph.security.configuration;


import com.isylph.basis.security.BaseSecurityConfig;
import com.isylph.security.jwt.JWTAuthenticationFilter;
import com.isylph.security.jwt.JWTAuthenticationProvider;
import com.isylph.security.jwt.JWTUserPasswordAuthenticationFilter;
import com.isylph.security.jwt.UserTokenAssemblerService;
import com.isylph.security.service.LongUserDetailsService;
import com.isylph.security.service.RestApiUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    @Autowired
    private IgnoreUrlConfig ignoreUrlConfig;

    @Autowired
    private DynamicAuthorizationManager authorizationManager;

    private final List<String> finalIgnoredUrls = new ArrayList<>(ignoreUrls);


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        finalIgnoredUrls.addAll(ignoreUrlConfig.getUrlList());
        String[] array = finalIgnoredUrls.toArray(new String[0]);
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(array).permitAll()
                        .anyRequest().access(authorizationManager))
                .addFilterBefore(new JWTAuthenticationFilter(userTokenAssemblerService, longUserDetailsService), AuthorizationFilter.class)
                .addFilterAt(new JWTUserPasswordAuthenticationFilter(authenticationManager, userTokenAssemblerService), UsernamePasswordAuthenticationFilter.class)
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
