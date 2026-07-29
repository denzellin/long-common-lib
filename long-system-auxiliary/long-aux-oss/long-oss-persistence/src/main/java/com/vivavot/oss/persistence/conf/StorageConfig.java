package com.vivavot.oss.persistence.conf;


import com.vivavot.oss.external.impl.FileStorageImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Bean
    public FileStorageImpl ossClientTool() {
        return new FileStorageImpl();
    }
}
