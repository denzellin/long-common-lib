package com.isylph.uid.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.fsg.uid.UidGenerator;
import org.springframework.stereotype.Service;

@Service
public class UidService {

    @Autowired
    private UidGenerator uidGenerator;


    public Long getUid() {

        long uid = uidGenerator.getUID();

        return uid;
    }

}
