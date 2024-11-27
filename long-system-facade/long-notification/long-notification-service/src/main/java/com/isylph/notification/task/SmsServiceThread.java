package com.isylph.notification.task;

import com.isylph.notification.beans.SendSmsReq;
import com.isylph.notification.service.PostSmsService;
import com.isylph.notification.service.SmsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.EnableRetry;

@Slf4j
@EnableRetry
public class SmsServiceThread implements Runnable {

    private SmsClient client;

    public SmsServiceThread(SmsClient c) throws Exception {
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
                SendSmsReq smsRequest = PostSmsService.takeSms();
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
