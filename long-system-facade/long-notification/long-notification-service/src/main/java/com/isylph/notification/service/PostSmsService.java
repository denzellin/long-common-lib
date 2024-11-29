package com.isylph.notification.service;


/**
 * 检测一个手机号码是否在指定时间内(如60s)重复发起短信发送
 * 检测一个手机号码是否在一天内发送的短信超额
 *
 *
 */


import com.isylph.basis.consts.BaseErrorConsts;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.notification.beans.SendSmsReq;
import com.isylph.notification.task.SmsServiceThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@EnableRetry
@Service
public class PostSmsService {


    private static final BlockingQueue<SendSmsReq> smsQueue = new ArrayBlockingQueue<>(1500);//短信发送队列

    private static final ConcurrentHashMap<String, Long> smsSendTimeMap = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, Integer> smsSendCountMap = new ConcurrentHashMap<>();


    @Value("${sms.threshold.interval:60}")
    private Integer timeIntervalBetweenSms;

    @Value("${sms.threshold.daily-count:20}")
    private Integer dailySmsCntThreshold;

    @Value("${sms.threshold.retry-on-fail:3}")
    private Integer retryCnt;

    @Value("${sms.task.thread:1}")
    private Integer smsThreadCnt;

    @Value("${sms.task.service}")
    private String smsClientService;


    @Autowired
    private Map<String, SmsClient> smsClients;

    @PostConstruct
    public void init() throws Exception {
        if (smsClients == null || smsClients.get(smsClientService) == null){
            log.error("Failed to init sms service, failed to get sms service: {}", smsClientService);
            throw new Exception();
        }
        SmsClient client = smsClients.get(smsClientService);

        log.info("Start {} thread for sending sms.",smsThreadCnt);
        for (int i = 0; i < smsThreadCnt; i++) {
            new Thread(new SmsServiceThread(client)).start();
        }
    }

    public void addSms(SendSmsReq sms) {
        String phone = sms.getPhone();
        checkSendTimeAndCount(phone);
        smsQueue.add(sms);
    }


    public static SendSmsReq takeSms() throws InterruptedException {
        return smsQueue.take();
    }

    @Bean
    public RetryTemplate retryTemplate() {
        /*
        第一次加载或配置有变化时重新构造
         */

        // 构建重试模板实例
        RetryTemplate retryTemplate = new RetryTemplate();

        // 设置重试策略，主要设置重试次数
        SimpleRetryPolicy policy = new SimpleRetryPolicy(this.retryCnt, Collections.singletonMap(Exception.class, true));
        retryTemplate.setRetryPolicy(policy);

        // 设置重试回退操作策略，主要设置重试间隔时间
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(100);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        return retryTemplate;
    }


    private void checkSendTimeAndCount(String phoneNumber) {

        long lastSendTime = Optional.ofNullable(smsSendTimeMap.get(phoneNumber)).orElse(0L);
        long curSendTime = System.currentTimeMillis();
        if (curSendTime - lastSendTime < timeIntervalBetweenSms * 1000) {
            throw new ReturnException(BaseErrorConsts.RET_ERROR, "一分钟内不能重复发送");
        }

        int sendCount = Optional.ofNullable(smsSendCountMap.get(phoneNumber)).orElse(0);
        if (sendCount > dailySmsCntThreshold) {
            throw new ReturnException(BaseErrorConsts.RET_ERROR, "同一手机号一天只能发送10条短信");
        }

        smsSendTimeMap.put(phoneNumber, curSendTime);
        smsSendCountMap.put(phoneNumber, ++sendCount);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void clear() {
        smsSendTimeMap.clear();
        smsSendCountMap.clear();
    }
}
