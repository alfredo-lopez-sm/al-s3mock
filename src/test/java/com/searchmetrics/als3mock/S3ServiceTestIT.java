package com.searchmetrics.als3mock;

import com.adobe.testing.s3mock.junit5.S3MockExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest
@ExtendWith({S3MockExtension.class})
class S3ServiceTestIT {

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
}