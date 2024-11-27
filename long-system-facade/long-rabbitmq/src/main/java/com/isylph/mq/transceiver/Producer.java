package com.isylph.mq.transceiver;


import com.isylph.mq.config.RabbitConfig;
import com.isylph.mq.service.MessageSnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Producer implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private MessageSnService messageSnService;

    @Autowired
    private RabbitConfig rabbitConfig;

    /**
     * 由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
     */
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public Producer(RabbitTemplate rabbitTemplate) {

        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
    }


    public void sendMsg(String content, String router) {
        Long uid = messageSnService.getMessageSn();
        CorrelationData correlationId = new CorrelationData(uid.toString());

        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(rabbitConfig.getExchanger(), router,
                content, correlationId);
    }

    /**
     * 回调
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("*********************send mq message call back id:" + correlationData);
        if (ack) {
            log.info("********************* successfully");
        } else {
            log.info("********************* failed: {}", cause);
        }
    }
}
