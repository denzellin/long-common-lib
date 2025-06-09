package com.isylph.notification.service.impl;



import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;
import com.isylph.notification.entity.SendSmsCmd;
import com.isylph.utils.json.JacksonUtils;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class AliSmsClient {

    @Value("${sms.ali.endpoint}")
    private String endpoint;

    @Value("${sms.ali.access-key-id}")
    private String accessKeyId;

    @Value("${sms.ali.secret}")
    private String accessKeySecret;

    @Value("${sms.ali.signature}")
    private String signature;

    @Value("${sms.logging:false}")
    private Boolean loggingSms;

    private AsyncClient aliClient;

    @PostConstruct
    public void init(){
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                // Please ensure that the environment variables ALIBABA_CLOUD_ACCESS_KEY_ID and ALIBABA_CLOUD_ACCESS_KEY_SECRET are set.
                .accessKeyId(accessKeyId)
                .accessKeySecret(accessKeySecret)
                //.securityToken(System.getenv("ALIBABA_CLOUD_SECURITY_TOKEN")) // use STS token
                .build());

        aliClient = AsyncClient.builder()
                //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
                .credentialsProvider(provider)
                //.serviceConfiguration(Configuration.create()) // Service-level configuration
                // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
                                .setEndpointOverride(endpoint)
                        //.setConnectTimeout(Duration.ofSeconds(30))
                )
                .build();
    }


    public void send(SendSmsCmd req) {

        log.debug("Try to send sms: {}", req);
        String content = JacksonUtils.serialize(req.getParams());


        // Parameter settings for API request
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .phoneNumbers(req.getPhone())
                .signName(signature)
                .templateCode(req.getTemplateCode())
                .templateParam(content)
                // Request-level configuration rewrite, can set Http request parameters, etc.
                // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                .build();

        CompletableFuture<SendSmsResponse> response = aliClient.sendSms(sendSmsRequest);


        try {
            SendSmsResponse resp = response.get();
            System.out.println(new Gson().toJson(resp));
        } catch (InterruptedException | ExecutionException e) {
            log.info("Send sms failed", e);
        }

        if(loggingSms){
            log.info("Succeeded to send sms, {}", req);
        }
    }
}
