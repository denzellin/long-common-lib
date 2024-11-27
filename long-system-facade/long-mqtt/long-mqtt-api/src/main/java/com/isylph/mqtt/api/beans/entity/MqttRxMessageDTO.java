package com.isylph.mqtt.api.beans.entity;

import com.isylph.basis.beans.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class MqttRxMessageDTO<T> extends BaseDTO {

    /**
     * mqtt_id
     */
    private Integer mqttId;

    /**
     * mqtt_duplicate
     */
    private Boolean duplicate;

    /**
     * mqtt_receivedRetained
     */
    private Boolean receivedRetained;

    /**
     * id
     */
    private UUID uuid;

    /**
     * mqtt_receivedTopic
     */
    private String receivedTopic;

    /**
     * mqtt_receivedQos
     */
    private Integer receivedQos;

    /**
     * timestamp
     */
    private Long timestamp;

    private T payload;

}
