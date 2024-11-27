package com.isylph.mq.service;


public interface ReceiveMessageService {

    void  consumeMessage(String topic, String tags, String content);
}
