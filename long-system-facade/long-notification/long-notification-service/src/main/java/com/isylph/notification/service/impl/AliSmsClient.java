package com.isylph.notification.service.impl;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isylph.notification.beans.SendSmsReq;
import com.isylph.notification.beans.SmsMap;
import com.isylph.notification.model.SysSmsLogPO;
import com.isylph.notification.service.SmsClient;
import com.isylph.notification.service.SysSmsLogService;
import com.isylph.utils.json.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service("aliSmsClient")
public class AliSmsClient implements SmsClient {

    @Value("${sms.ali.region-id}")
    private String regionId;

    @Value("${sms.ali.access-key-id}")
    private String accessKeyId;

    @Value("${sms.ali.secret}")
    private String secret;

    @Value("${sms.ali.signature}")
    private String signature;

    @Value("${sms.logging:true}")
    private Boolean loggingSms;



    private DefaultProfile profile;

    private IAcsClient client;

    @Autowired
    private SysSmsLogService sysSmsLogService;

    @PostConstruct
    void init(){
        profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        client = new DefaultAcsClient(profile);
    }

    @Override
    public boolean send(SendSmsReq smsRequest) {

        log.debug("Try to send sms: {}", smsRequest);
        List<SmsMap> contentList = smsRequest.getContentList();
        String content = "";
        if (CollectionUtils.isEmpty(contentList)){
            log.warn("empty content");
        }else {
            ObjectNode jo = JacksonUtils.createNode();
            for (SmsMap item: contentList) {
                jo.put(item.getKey(), item.getValue());
            }

            content = JacksonUtils.serialize(jo);
        }
        log.debug("Assembled content to start the request: {}", content);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("PhoneNumbers", smsRequest.getPhone());
        request.putQueryParameter("SignName", signature);
        request.putQueryParameter("TemplateCode", smsRequest.getTemplateCode());
        request.putQueryParameter("TemplateParam", content);
        try {
            log.debug("Try to start the request: {}", request);

            log.debug("Start {}, {}, {}, {}, {}", regionId, accessKeyId, secret, signature, client);
            CommonResponse response = client.getCommonResponse(request);
            log.info("sms request result: {}, {}, {}",response.getHttpStatus(), response.getHttpResponse(), response.getData());
            if (loggingSms){
                SysSmsLogPO sms = new SysSmsLogPO()
                        .setCode(smsRequest.getTemplateCode())
                        .setMobile(smsRequest.getPhone())
                        .setContent(content);
                sysSmsLogService.save(sms);
            }

        } catch (ServerException e) {
            log.warn("failed to send sms for : ServerException{}", e);
            log.error("{}", e);
            return false;
        } catch (ClientException e) {
            log.warn("failed to send sms for ClientException: {}", e);
            log.error("{}", e);
            return false;
        } catch (RuntimeException e){
            log.warn("failed to send sms for RuntimeException: {}", e);
            log.error("{}", e);
            return false;
        }catch (Exception e){
            log.warn("failed to send sms for exception: {}", e);
            log.error("{}", e);
            return false;
        }

        log.debug("sms done");

        return false;
    }
}
