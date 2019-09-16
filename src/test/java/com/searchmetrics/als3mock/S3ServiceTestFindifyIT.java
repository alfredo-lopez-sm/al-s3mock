package com.searchmetrics.als3mock;

import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.auth.credentials.AnonymousCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest(classes = {S3ServiceTestFindifyIT.S3ClientMockConfig.class, S3Service.class})
class S3ServiceTestFindifyIT {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private S3Service s3Service;

    @Test
    @DisplayName("returns a list of all the buckets")
    void getList() {

        final CreateBucketRequest createBucketRequest = CreateBucketRequest.builder().bucket("hello").build();
        s3Client.createBucket(createBucketRequest);

        List<String> actual = s3Service.getBuckets();

        assertThat(actual).containsExactly("hello");

    }

    @TestConfiguration
    static class S3ClientMockConfig {

        @Bean
        public S3Mock  s3MockApi() {
            S3Mock api = new S3Mock.Builder().build();
            api.start();
            return api;
        }

        @Bean
        public S3Client s3Client() throws URISyntaxException {
            String mockUrl = "http://localhost:8001";
            return S3Client.builder()
                    .credentialsProvider(AnonymousCredentialsProvider.create())
                    .region(Region.EU_CENTRAL_1)
                    .endpointOverride(new URI(mockUrl))
                    .build();
        }
    }
}