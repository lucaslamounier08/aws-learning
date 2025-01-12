package com.lucaslamounier.aws.s3.service;

import io.awspring.cloud.s3.S3Template;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class StorageServiceIntegrationTest {

    private static final LocalStackContainer localStackContainer;
    private static final String BUCKET_NAME = "my-personal-bucket";

    @Autowired
    private S3Template s3Template;

    @Autowired
    private StorageService storageService;

    static {
        localStackContainer = new LocalStackContainer(DockerImageName.parse("localstack/localstack:3.4"))
                .withCopyFileToContainer(MountableFile.forClasspathResource("init-s3-bucket.sh", 0744), "/etc/localstack/init/ready.d/init-s3-bucket.sh")
                .withServices(LocalStackContainer.Service.S3)
                .waitingFor(Wait.forLogMessage(".*Executed init-s3-bucket.sh.*", 1));
        localStackContainer.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.aws.credentials.access-key", localStackContainer::getAccessKey);
        registry.add("spring.cloud.aws.credentials.secret-key", localStackContainer::getSecretKey);
        registry.add("spring.cloud.aws.s3.region", localStackContainer::getRegion);
        registry.add("spring.cloud.aws.s3.endpoint", localStackContainer::getEndpoint);

        // custom properties
        registry.add("app.aws.s3.bucketName", () -> BUCKET_NAME);
    }

    @Test
    void shouldSaveFileSuccessfullyToBucket() throws IOException {
        // Arrange
        var key = RandomString.make(10) + ".txt";
        var fileContent = RandomString.make(50);
        var fileToUpload = createTextFile(key, fileContent);

        // Act
        storageService.save(fileToUpload);

        // Assert
        var isFileSaved = s3Template.objectExists(BUCKET_NAME, key);
        assertThat(isFileSaved).isTrue();
    }

    @Test
    void shouldFetchSavedFileSuccessfullyFromBucket() throws IOException {
        // Arrange
        var key = RandomString.make(10) + ".txt";
        var fileContent = RandomString.make(50);
        var fileToUpload = createTextFile(key, fileContent);
        storageService.save(fileToUpload);

        // Act
        var retrievedObject = storageService.retrieve(key);

        // Assert
        var retrievedContent = readFile(retrievedObject.getContentAsByteArray());
        assertThat(retrievedContent).isEqualTo(fileContent);
    }

    @Test
    void shouldDeleteFileFromBucketSuccessfully() throws IOException {
        // Arrange
        var key = RandomString.make(10) + ".txt";
        var fileContent = RandomString.make(50);
        var fileToUpload = createTextFile(key, fileContent);
        storageService.save(fileToUpload);

        // Verify that the file is saved successfully in S3 bucket
        var isFileSaved = s3Template.objectExists(BUCKET_NAME, key);
        assertThat(isFileSaved).isTrue();

        // Act
        storageService.delete(key);

        // Assert
        isFileSaved = s3Template.objectExists(BUCKET_NAME, key);
        assertThat(isFileSaved).isFalse();
    }

    private String readFile(byte[] bytes) {
        var inputStreamReader = new InputStreamReader(new ByteArrayInputStream(bytes));
        return new BufferedReader(inputStreamReader).lines().collect(Collectors.joining("\n"));
    }

    private MultipartFile createTextFile(String fileName, String content) throws IOException {
        var fileContentBytes = content.getBytes();
        var inputStream = new ByteArrayInputStream(fileContentBytes);
        return new MockMultipartFile(fileName, fileName, "text/plain", inputStream);
    }

}