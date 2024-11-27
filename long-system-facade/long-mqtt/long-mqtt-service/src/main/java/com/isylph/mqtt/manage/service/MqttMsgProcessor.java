package com.isylph.mqtt.manage.service;

import com.isylph.mqtt.api.beans.entity.MqttRxMessageDTO;


public interface MqttMsgProcessor {

    void procMsg(MqttRxMessageDTO msg);

}
