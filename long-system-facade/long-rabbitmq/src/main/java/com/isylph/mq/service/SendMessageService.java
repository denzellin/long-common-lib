package com.isylph.mq.service;

import com.isylph.mq.transceiver.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SendMessageService {

    @Autowired
    private Producer producer;

    public void sendMessage(String route, String content){
        producer.sendMsg(content, route);
    }

    public void sendMessage(String route, String tag, String content){

        producer.sendMsg(content, route+"."+tag);
    }
}
