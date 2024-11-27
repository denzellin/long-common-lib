package com.isylph.mqtt.manage.config;

import com.isylph.mqtt.manage.service.MqttMsgProcessor;
import com.isylph.mqtt.transceiver.IMqttSender;
import com.isylph.mqtt.transceiver.MqttInitiator;
import com.isylph.mqtt.transceiver.MqttMsgDispatcher;
import com.isylph.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public abstract class MqttConfigManager {

    public static final String mqttInboundBean = "inbound";

    @Autowired
    private MqttMsgDispatcher mqttMsgDispatcher;

    @Autowired
    private MqttInitiator mqttInitiator;


    @Autowired
    private IMqttSender mqttSender;

    public IMqttSender getMqttSender(){
        return mqttSender;
    }

    public static String getMqttInboundBean() {
        return mqttInboundBean;
    }

    public void registerProcessor(String topicPatten, MqttMsgProcessor processor){
        addTopic(topicPatten);
        mqttMsgDispatcher.registerProcessor(topicPatten, processor);
    }
    public void removeProcessor(String topicPatten){
        removeTopic(topicPatten);
        mqttMsgDispatcher.removeProcessor(topicPatten);
    }

    public void registerDefaultProcessor(MqttMsgProcessor processor){
        mqttMsgDispatcher.registerDefaultProcessor(processor);
    }

    public List<String> getTopics(){
        List<String> topics = null;

        if (mqttInitiator != null){
            topics = Arrays.asList(mqttInitiator.getTopic());
        }

        return topics;
    }

    public void addTopic(String topic){
        if (StringUtils.isEmpty(topic)){
            return;
        }

        if (mqttInitiator != null){
            mqttInitiator.addTopic(topic);
        }
    }

    public void removeTopic(String topic){
        if (StringUtils.isEmpty(topic)){
            return;
        }

        if (mqttInitiator != null){
            mqttInitiator.removeTopic(topic);
        }
    }

    public void sendMessage(String data){
        mqttSender.sendToMqtt(data);
    }
    public void sendMessage(String topic,
                            String payload){
        mqttSender.sendToMqtt(topic, payload);
    }
    public void sendMessage(String topic, byte[] data){
        mqttSender.sendToMqtt(topic,data);
    }

    public void sendMessage(String topic, int qos, String payload){
        mqttSender.sendToMqtt(topic,qos,payload);
    }
}
