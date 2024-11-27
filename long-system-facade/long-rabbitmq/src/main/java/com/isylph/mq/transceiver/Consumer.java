package com.isylph.mq.transceiver;

import com.isylph.mq.service.ReceiveMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RabbitListener(queues = "${rabbitmq.consumer.queue}")
public class Consumer {

    @Autowired
    private ReceiveMessageService receiveMessageService;



    @RabbitHandler
    public void processByte(@Payload byte[] message, @Headers Map<String,Object> headers) {

        Integer length = message.length;
        String ret = new String(message);

        process(ret);

        log.info("-------------------------------Got byte message from queue, length:{}, content: {}", length,ret);
    }

    @RabbitHandler
    public void processStr(@Payload String message, @Headers Map<String,Object> headers) {
        process(message);
        log.info("-------------------------------Got String message from queue: {}", message);
    }


    private void process (String msg){

        String topic = "";
        String tags = "";
        receiveMessageService.consumeMessage(topic, tags, msg);
    }
}
