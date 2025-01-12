package com.lucaslamounier.aws.s3.controller;

import com.lucaslamounier.aws.s3.port.StoragePort;
import io.awspring.cloud.s3.S3Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/storage")
public class StorageController {

    private final StoragePort storagePort;

    @PostMapping
    public ResponseEntity<Void> save(@RequestParam("file") MultipartFile file) throws IOException {
        storagePort.save(file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{objectKey}")
    public ResponseEntity<Void> delete(@PathVariable String objectKey) {
        storagePort.delete(objectKey);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{objectKey}")
    public ResponseEntity<String> retrieve(@PathVariable String objectKey) throws IOException {
        S3Resource s3Resource = storagePort.retrieve(objectKey);
        return new ResponseEntity<>(new String(s3Resource.getInputStream().readAllBytes()), HttpStatus.OK);
    }
}
