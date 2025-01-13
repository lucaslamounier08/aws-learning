package com.lucaslamounier.aws.sqs.port;

import com.lucaslamounier.aws.sqs.dto.MyEvent;

public interface PublisherPort {
    void publish(MyEvent message);
}
