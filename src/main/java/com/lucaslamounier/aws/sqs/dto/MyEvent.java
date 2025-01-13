package com.lucaslamounier.aws.sqs.dto;

import java.io.Serializable;

public record MyEvent(String content) implements Serializable {
}
