package com.isylph.console.conf;

import com.isylph.basis.controller.conf.SpringMvcBaseConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

/**
 * 打开@EnableWebMvc会导致swagger-ui不能用
 */
//
//@EnableWebMvc
@Configuration
public class ConsoleSpringMvcConfig extends SpringMvcBaseConfig {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        /**
         * 不需要配置swagger的资源路径, swagger在jar包中已自行配置
         */
        /**
         * 配置静态资源路径时, 需要在spring security中放开对应的静态资源的路径
         * com.isylph.lb.basis.security.BaseSecurityConfig#baseConfigure
         */

        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(
                    CLASSPATH_RESOURCE_LOCATIONS);
        }

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addRedirectViewController("/", "index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }


}
