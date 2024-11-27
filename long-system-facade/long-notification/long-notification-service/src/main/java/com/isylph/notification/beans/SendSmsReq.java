package com.isylph.notification.beans;


import com.isylph.basis.base.BaseCmd;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SendSmsReq extends BaseCmd {

    private String phone;

    private List<SmsMap> contentList; // 短信正文

    private String templateCode; // 模板code

    private long timestamp;

    public SendSmsReq() {
        super();
        timestamp = 0;
    }

}
