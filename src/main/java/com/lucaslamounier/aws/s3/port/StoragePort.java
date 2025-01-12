package com.lucaslamounier.aws.s3.port;

import io.awspring.cloud.s3.S3Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StoragePort {
    void save(MultipartFile file) throws IOException;

    S3Resource retrieve(String objectKey);

    void delete(String objectKey);
}
