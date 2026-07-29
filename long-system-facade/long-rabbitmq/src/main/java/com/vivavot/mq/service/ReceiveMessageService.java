package com.vivavot.mq.service;


public interface ReceiveMessageService {

    void  consumeMessage(String topic, String tags, String content);
}
