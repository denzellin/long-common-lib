package com.isylph.oss.persistence.conf;


import com.isylph.oss.external.impl.OSSClientTool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Bean
    public OSSClientTool ossClientTool() {
        return new OSSClientTool();
    }
}
