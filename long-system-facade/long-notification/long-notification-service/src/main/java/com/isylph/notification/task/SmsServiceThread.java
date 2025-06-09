package com.isylph.notification.task;

import com.isylph.notification.entity.SendSmsCmd;
import com.isylph.notification.service.impl.AliSmsClient;
import com.isylph.notification.service.impl.SmsRemoteService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsServiceThread implements Runnable {

    private AliSmsClient client;

    public SmsServiceThread(AliSmsClient c) throws Exception {
        if (null == c){
            log.error("null sms client");
            throw new Exception();
        }
       client = c;
    }

    @Override
    public void run() {
        try {

            while (true) {

                //从队列取出,队列为空时阻塞
                SendSmsCmd smsRequest = SmsRemoteService.takeSms();
                //发送短信，失败后重试
                try {
                    client.send(smsRequest);
                }catch (Exception e){
                    log.warn("Error: {}", e);
                }

                Thread.sleep(100);

            }
        } catch (InterruptedException e) {
            log.error("短信发送线程被中断", e);
        }
    }
}
