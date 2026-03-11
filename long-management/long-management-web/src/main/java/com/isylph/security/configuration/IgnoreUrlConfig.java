package com.isylph.security.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @Author Denzel Lin
 * @Date 2026/3/11 18:30
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "security.ignore")
public class IgnoreUrlConfig {

    private List<String> urls = new ArrayList<>();

    public String[] getUrls() {
        return urls.toArray(new String[0]);
    }

    public List<String> getUrlList() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
