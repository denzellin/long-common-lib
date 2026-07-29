package com.vivavot.console.utils;


import com.vivavot.console.api.service.RetCodeUtilService;
import com.vivavot.error.model.AuxErrorMessagePO;
import com.vivavot.error.service.AuxErrorMessageService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class RetCodeUtil implements RetCodeUtilService {

    private static Map<Long, String> errorMessageMap = null;

    @Autowired
    private AuxErrorMessageService auxErrorMessageService;

    private static AuxErrorMessageService localErrMessageService;

    @PostConstruct
    public void init(){
        RetCodeUtil.localErrMessageService = auxErrorMessageService;
        load();
    }

    @Scheduled(cron = "0 0/20 * * * ? ")
    public void load() {

        if (localErrMessageService == null){
            log.debug("util on initiating...");
            return;
        }

        List<AuxErrorMessagePO> msgs = localErrMessageService.list();
        if (null == msgs || msgs.isEmpty()){
            log.debug("not error message set");
            return;
        }

        Map<Long, String> map = new ConcurrentHashMap<>();

        for(AuxErrorMessagePO msg: msgs){
            map.put(msg.getId(), msg.getMessage());
        }

        errorMessageMap = map;
    }

    public String getMessage(Long key) {
        if (null == key){
            return "";
        }
        if (errorMessageMap == null){
            return "系统错误： "+ key.toString();
        }
        String msg = errorMessageMap.get(key);
        if (StringUtils.isEmpty(msg)){
            return "系统错误： "+ key.toString();
        }

        return msg;
    }
}
