package com.isylph.notification.api.service;


import java.util.List;
import java.util.Map;

public interface UnifiedMessageService {

    void unifiedSendSms(String phoneNo, String templateCode, Map<String, String> contentMap);

    void sendSimpleMailMessage(List<String> recipients, String subject, String text);

    void sendHtmlMailMessage(String templateFileName, List<String> recipients, String subject, Map<String, String> model);
}
