package com.isylph.notification.rpc;


import com.isylph.notification.api.service.UnifiedMessageService;
import com.isylph.notification.beans.SendSmsReq;
import com.isylph.notification.beans.SmsMap;
import com.isylph.notification.service.PostSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service("unifiedMessageService")
public class SmsRemoteService implements UnifiedMessageService {


    @Autowired
    private PostSmsService messageService;

    @Override
    public void unifiedSendSms(String phoneNo, String templateCode, Map<String, String> contentMap) {
        SendSmsReq sms = new SendSmsReq();
        sms.setPhone(phoneNo);
        sms.setTemplateCode(templateCode);
        ArrayList<SmsMap> contentList = new ArrayList<>();
        for (Map.Entry<String, String> entry : contentMap.entrySet()) {
            SmsMap smsMap = new SmsMap();
            smsMap.setKey(entry.getKey());
            smsMap.setValue(entry.getValue());
            contentList.add(smsMap);
        }
        sms.setContentList(contentList);
        messageService.addSms(sms);
        return;
    }
}
