package com.axess.smartbankapi.sqs;

import com.axess.smartbankapi.ses.EMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.axess.smartbankapi.ses.Email;

import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Consumer {

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    @Autowired
    EMailService eMailService;

    @SqsListener(value = "${cloud.aws.end-point.uri}",deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void processMessage(TestMessage message) {
        log.info("Message from SQS {}", message.getMessage());
        Email email = new Email();
        email.setBody(message.getMessage());
        email.setFrom("admin@cloudtech-training.com");
        email.setTo("wengjiancong1994@gmail.com");
        email.setSubject("kevin placed order");
        eMailService.sendEmail(email);
        log.info("Send email to the customer thanking for kevin's order");

    }
}