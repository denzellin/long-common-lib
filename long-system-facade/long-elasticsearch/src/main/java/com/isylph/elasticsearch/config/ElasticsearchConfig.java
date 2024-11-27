package com.isylph.elasticsearch.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Configuration
public class ElasticsearchConfig {

    /**
     * ES地址,ip:port
     */
    @Value("${elasticsearch.address}")
    private String[] address;

    final private CredentialsProvider credentialsProvider = new BasicCredentialsProvider();


    @Bean
    public RestClientBuilder restClientBuilder() {

        HttpHost[] hosts = Arrays.stream(address)
                .map(this::makeHttpHost)
                .filter(Objects::nonNull)
                .toArray(HttpHost[]::new);

        log.debug("hosts:{}", Arrays.toString(hosts));

        //配置权限验证
        //credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        RestClientBuilder restClientBuilder = RestClient.builder(hosts).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        });
        return restClientBuilder;
    }


    @Bean(name = "highLevelClient")
    public RestHighLevelClient highLevelClient(@Autowired RestClientBuilder restClientBuilder) {
        //restClientBuilder.setMaxRetryTimeoutMillis(60000);
        return new RestHighLevelClient(restClientBuilder);
    }


    private HttpHost makeHttpHost(String s) {

        String text = s;
        String scheme = "http";
        int schemeIdx = s.indexOf("://");
        if (schemeIdx > 0) {
            scheme = s.substring(0, schemeIdx);
            text = s.substring(schemeIdx + 3);
        }

        String[] address = text.split(":");
        String ip = address[0];
        int port = Integer.parseInt(address[1]);

        return new HttpHost(ip, port, scheme);
    }
}
