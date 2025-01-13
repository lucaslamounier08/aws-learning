package com.lucaslamounier.aws.sqs.configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@Configuration
public class AwsSqsProperties {

    @NotBlank
    @Value("${app.aws.sqs.endpoint}")
    private String endpoint;

    @NotBlank
    @Value("${app.aws.sqs.queue-name}")
    private String queueName;

    @NotBlank
    @Value("${spring.cloud.aws.credentials.access-key}")
    private String accessKey;

    @NotBlank
    @Value("${spring.cloud.aws.credentials.secret-key}")
    private String secretKey;

}