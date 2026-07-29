package com.vivavot.mqtt.manage.service;

import com.vivavot.mqtt.api.beans.entity.MqttRxMessageDTO;


public interface MqttMsgProcessor {

    void procMsg(MqttRxMessageDTO msg);

}
