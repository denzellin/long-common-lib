package com.isylph.security.beans;

import com.isylph.security.service.RestApiUrlService;
import com.isylph.utils.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.util.AntPathMatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlAuthCollection {

    private final static String HTTP_ALL = "HTTP_ALL";


    private final AntPathMatcher antPathMatcher = new AntPathMatcher();


    /**
     * Map<http Url, Map<method, role list></Url>
     *
     * http method: 不同HTTP请求防范分组，如果是所有方法，则存入
     */
    private Map<String, Map<String, String>> urlAuthMaps;


    public UrlAuthCollection() {
        urlAuthMaps = new HashMap<>();
    }

    public void add(String url, String method, List<String> roles){

        method = StringUtils.isEmpty(method)?HTTP_ALL:method;

        Map<String, String> urlMap = urlAuthMaps.get(url);
        if (urlMap == null){
            urlMap = new HashMap<>();
            urlAuthMaps.put(url, urlMap);
        }
        urlMap.put(method.toUpperCase(), String.join(",", roles));
    }

    public List<ConfigAttribute> getAuth(String httpMethod, String url){
        int s = url.indexOf("?");
        // 请求URL如果带参数，会导致匹配错误
        if( s >= 0){
            url = url.substring(0, s);
        }
        for(Map.Entry<String,Map<String, String>> entry: urlAuthMaps.entrySet()){
            if(antPathMatcher.match(entry.getKey(),url)){
                Map<String, String> auth = entry.getValue();
                String roles = auth.get(httpMethod.toUpperCase());
                if (StringUtils.isEmpty(roles)){
                    roles = auth.get(HTTP_ALL);
                }
                if (StringUtils.isNotEmpty(roles)) {
                    return SecurityConfig.createListFromCommaDelimitedString(roles);
                }/*else{
                    return null;
                }*/
            }
        }
        return SecurityConfig.createListFromCommaDelimitedString(RestApiUrlService.ADMIN_ROLE);
    }

}
