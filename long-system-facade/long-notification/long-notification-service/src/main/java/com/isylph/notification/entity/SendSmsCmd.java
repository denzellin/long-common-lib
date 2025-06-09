package com.isylph.notification.entity;


import com.fasterxml.jackson.databind.JsonNode;
import com.isylph.basis.base.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SendSmsCmd extends BaseCmd {

    private String phone;

    private JsonNode params;

    private String templateCode; // 模板code

    private long timestamp;

    public SendSmsCmd() {
        super();
        timestamp = 0;
    }

    @Override
    public String toString() {
        return "SendSmsCmd{" +
                "phone='" + phone + '\'' +
                ", params=" + params +
                ", templateCode='" + templateCode + '\'' +
                '}';
    }
}
