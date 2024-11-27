package com.isylph.notification.api.service;


import java.util.Map;

public interface UnifiedMessageService {

    void unifiedSendSms(String phoneNo, String templateCode, Map<String, String> contentMap);

}
