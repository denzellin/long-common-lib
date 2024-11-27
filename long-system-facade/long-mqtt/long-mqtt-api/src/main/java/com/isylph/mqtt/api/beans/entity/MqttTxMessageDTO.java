package com.isylph.mqtt.api.beans.entity;

import com.isylph.basis.beans.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class MqttTxMessageDTO<T> extends BaseDTO {

    private LocalDateTime timestamp;

    private String topic;

    private T payload;

    public MqttTxMessageDTO(String topic, T payload) {
        this.topic = topic;
        this.payload = payload;
        this.timestamp = LocalDateTime.now();
    }
}
