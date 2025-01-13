package com.lucaslamounier.aws.sqs.consumer;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyConsumer {

    @SqsListener("${app.aws.sqs.queue-name}")
    public void listen(Message<?> message) {
        log.info("Message received. Payload: {}. Headers: {}", message.getPayload(), message.getHeaders());
    }
}