package com.isylph.notification.service;


import com.isylph.notification.beans.SendSmsReq;

public interface SmsClient {

    boolean send(SendSmsReq smsRequest);

}
