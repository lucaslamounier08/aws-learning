package com.lucaslamounier.aws.sqs.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucaslamounier.aws.sqs.configuration.AwsSqsProperties;
import com.lucaslamounier.aws.sqs.dto.MyEvent;
import com.lucaslamounier.aws.sqs.exception.MapperException;
import com.lucaslamounier.aws.sqs.port.PublisherPort;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MyPublisher implements PublisherPort {

    private final SqsTemplate sqsTemplate;
    private final AwsSqsProperties awsSqsProperties;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(MyEvent message) {
        try {
            String payload = objectMapper.writeValueAsString(message);
            sqsTemplate.send(awsSqsProperties.getEndpoint(), payload);

            log.info("Message published: {}", message.content());
        } catch (JsonProcessingException e) {
            throw new MapperException("Erro ao converter mensagem: " + message, e);
        }
    }
}