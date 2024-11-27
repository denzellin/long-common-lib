package com.isylph.oss.persistence.conf;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class FileServerManager {

    private  String urlPrefix;

    private String localDir;

    private String type = "local";

    @PostConstruct
    public void init() {

        String tmp = urlPrefix.trim();
        if (tmp.endsWith("/")){
            urlPrefix = tmp;
        }else{
            urlPrefix+="/";
        }
        tmp = localDir.trim();
        if (tmp.endsWith("/")){
            localDir = tmp;
        }else{
            localDir+="/";
        }
    }

    public String assembleUrl(String path){
        return urlPrefix + path;
    }

}
