package com.isylph.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageSnServiceImpl  {

    @Autowired
    private RpcUidService rpcUidService;

    public Long getMessageSn() {
        return rpcUidService.getUid();
    }
}
