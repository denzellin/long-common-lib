package com.isylph.notification.service.impl;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isylph.notification.api.service.UnifiedMessageService;
import com.isylph.notification.entity.SendSmsCmd;
import com.isylph.notification.task.SmsServiceThread;
import com.isylph.utils.json.JacksonUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Slf4j
@Service
public class UnifiedMessageServiceImpl implements UnifiedMessageService {

    private static final BlockingQueue<SendSmsCmd> smsQueue = new ArrayBlockingQueue<>(1500);//短信发送队列

    private final Integer smsThreadCnt = Runtime.getRuntime().availableProcessors();

    @Autowired
    private AliSmsClient smsClient;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String mailFrom;


    @Override
    public void unifiedSendSms(String phoneNo, String templateCode, Map<String, String> contentMap) {
        SendSmsCmd sms = new SendSmsCmd();
        sms.setPhone(phoneNo);
        sms.setTemplateCode(templateCode);
        ObjectNode node = JacksonUtils.createNode();
        if (node == null){
            log.warn("failed to create json node");
            return;
        }
        for (Map.Entry<String, String> entry : contentMap.entrySet()) {
            node.put(entry.getKey(), entry.getValue());
        }
        sms.setParams(node);
        addSms(sms);
    }


    @Override
    public void sendSimpleMailMessage(List<String> recipients, String subject, String text) {
        if (CollectionUtils.isEmpty(recipients)){
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipients.toArray(new String[0]));
        message.setSubject(subject);
        message.setText(text);
        if (mailFrom != null && !mailFrom.isBlank()) {
            message.setFrom(mailFrom);
        }
        mailSender.send(message);
    }

    @Override
    public void sendHtmlMailMessage(String templateFileName, List<String> recipients, String subject, Map<String, String> model) {
        try {
            String body = renderTemplate(templateFileName, model);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());

            if (mailFrom != null && !mailFrom.isBlank()) {
                helper.setFrom(mailFrom);
            }
            helper.setTo(recipients.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(body, true); // true = isHtml
            mailSender.send(mimeMessage);
        } catch (IOException | MessagingException e) {
            throw new IllegalStateException("Failed to send HTML email", e);
        }
    }

    @PostConstruct
    public void init() throws Exception {

        log.info("Start {} thread for sending sms.",smsThreadCnt);
        for (int i = 0; i < smsThreadCnt; i++) {
            new Thread(new SmsServiceThread(smsClient)).start();
        }
    }

    public void addSms(SendSmsCmd sms) {
        smsQueue.add(sms);
    }


    public static SendSmsCmd takeSms() throws InterruptedException {
        return smsQueue.take();
    }




    private String renderTemplate(String templateFileName, Map<String, String> model) throws IOException {

        String template = Files.readString(Paths.get(templateFileName), StandardCharsets.UTF_8);

        if (model == null || model.isEmpty()) {
            return template;
        }

        String rendered = template;
        for (Map.Entry<String, String> e : model.entrySet()) {
            String key = e.getKey();
            String value = e.getValue() == null ? "" : escapeHtml(e.getValue());
            rendered = rendered.replace("{{" + key + "}}", value);
        }
        return rendered;
    }

    /**
     * 简单的 HTML 转义（覆盖常用字符）。如果需要更强的转义，请引入库如 Apache Commons Text。
     */
    private String escapeHtml(String input) {
        if (input == null) return "";
        StringBuilder out = new StringBuilder(Math.max(16, input.length()));
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch (c) {
                case '<' -> out.append("&lt;");
                case '>' -> out.append("&gt;");
                case '&' -> out.append("&amp;");
                case '"' -> out.append("&quot;");
                case '\'' -> out.append("&#x27;");
                case '/' -> out.append("&#x2F;");
                default -> out.append(c);
            }
        }
        return out.toString();
    }
}
