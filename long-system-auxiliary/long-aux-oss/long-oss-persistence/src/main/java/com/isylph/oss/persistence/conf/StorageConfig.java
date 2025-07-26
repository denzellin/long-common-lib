package com.isylph.oss.persistence.conf;


import com.isylph.oss.external.impl.FileStorageImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Bean
    public FileStorageImpl ossClientTool() {
        return new FileStorageImpl();
    }
}
