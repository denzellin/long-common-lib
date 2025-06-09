package com.isylph.notification.service.impl;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isylph.notification.api.service.UnifiedMessageService;
import com.isylph.notification.entity.SendSmsCmd;
import com.isylph.notification.task.SmsServiceThread;
import com.isylph.utils.json.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Slf4j
@Service
public class SmsRemoteService implements UnifiedMessageService {

    private static final BlockingQueue<SendSmsCmd> smsQueue = new ArrayBlockingQueue<>(1500);//短信发送队列

    private final Integer smsThreadCnt = Runtime.getRuntime().availableProcessors();

    @Autowired
    private AliSmsClient smsClient;


    @Override
    public void unifiedSendSms(String phoneNo, String templateCode, Map<String, String> contentMap) {
        SendSmsCmd sms = new SendSmsCmd();
        sms.setPhone(phoneNo);
        sms.setTemplateCode(templateCode);
        ObjectNode node = JacksonUtils.createNode();
        if (node == null){
            log.warn("failed to create json node");
            return;
        }
        for (Map.Entry<String, String> entry : contentMap.entrySet()) {
            node.put(entry.getKey(), entry.getValue());
        }
        sms.setParams(node);
        addSms(sms);
    }


    @PostConstruct
    public void init() throws Exception {

        log.info("Start {} thread for sending sms.",smsThreadCnt);
        for (int i = 0; i < smsThreadCnt; i++) {
            new Thread(new SmsServiceThread(smsClient)).start();
        }
    }

    public void addSms(SendSmsCmd sms) {
        smsQueue.add(sms);
    }


    public static SendSmsCmd takeSms() throws InterruptedException {
        return smsQueue.take();
    }
}
