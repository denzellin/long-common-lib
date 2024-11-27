package com.isylph.mqtt.transceiver;


import com.isylph.mqtt.api.beans.entity.MqttRxMessageDTO;
import com.isylph.mqtt.manage.service.MqttMsgProcessor;
import com.isylph.utils.StringUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MqttMsgDispatcher {

    private Map<String, MqttMsgProcessor> procMap = new HashMap<>();

    private Map<String, String[]> filterMap = new HashMap<>();

    private MqttMsgProcessor defaultProcessor;

    public void registerProcessor(String topicPatten, MqttMsgProcessor processor){
        if (StringUtils.isEmpty(topicPatten) || processor == null){
            return;
        }

        procMap.put(topicPatten, processor);
        filterMap.put(topicPatten, topicPatten.split("/"));
    }
    public void removeProcessor(String topicPatten){
        procMap.remove(topicPatten);
        filterMap.remove(topicPatten);
    }

    public void registerDefaultProcessor(MqttMsgProcessor processor){
        defaultProcessor = processor;
    }

    public void dispatch(Message msg){
        int cnt = 0;

        MessageHeaders header = msg.getHeaders();
        MqttRxMessageDTO rx = new MqttRxMessageDTO()
                .setMqttId(header.get("mqtt_id", Integer.class))
                .setDuplicate(header.get("mqtt_duplicate", Boolean.class))
                .setReceivedRetained(header.get("mqtt_receivedRetained", Boolean.class))
                .setUuid(header.getId())
                .setReceivedTopic(header.get("mqtt_receivedTopic", String.class))
                .setReceivedQos(header.get("mqtt_receivedQos", Integer.class))
                .setTimestamp(header.getTimestamp())
                .setPayload(msg.getPayload());

        for (Map.Entry<String, MqttMsgProcessor> entry : procMap.entrySet()){
            String topic = entry.getKey();
            MqttMsgProcessor processor = entry.getValue();

            if (match(filterMap.get(topic), rx.getReceivedTopic())){
                processor.procMsg(rx);
            }
        }

        if (cnt == 0 && defaultProcessor != null){
            defaultProcessor.procMsg(rx);
        }
    }





    private static boolean match(String[] filterTokens, String topic) {
        String[] topicTokens = topic.split("/");

        int filterLength = filterTokens.length;
        int topicLength = topicTokens.length;

        for (int i = 0; i < Math.min(filterLength, topicLength); i++) {
            String filterToken = filterTokens[i];
            String topicToken = topicTokens[i];

            if (!matchTokens(filterToken, topicToken)) {
                return false;
            }
        }
        if (filterTokens[filterLength - 1].equals("#")){
            return filterLength <= topicLength;
        }else{
            return filterLength == topicLength;
        }
    }

    private static boolean matchTokens(String filterToken, String topicToken) {
        if (filterToken.equals("#")) {
            return true;
        } else if (filterToken.equals("+")) {
            return !topicToken.isEmpty();
        } else {
            return filterToken.equals(topicToken);
        }
    }

}
