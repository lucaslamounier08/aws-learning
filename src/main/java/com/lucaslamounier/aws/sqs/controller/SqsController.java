package com.lucaslamounier.aws.sqs.controller;

import com.lucaslamounier.aws.sqs.dto.MyEvent;
import com.lucaslamounier.aws.sqs.port.PublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/sqs")
public class SqsController {

    private final PublisherPort publisherPort;

    @PostMapping
    public ResponseEntity<Void> publish(@RequestBody MyEvent myEvent) {
        publisherPort.publish(myEvent);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
