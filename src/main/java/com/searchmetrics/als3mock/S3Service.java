package com.searchmetrics.als3mock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    public List<String> getBuckets() {
        return s3Client
                .listBuckets()
                .buckets()
                .stream()
                .map(Bucket::name)
                .collect(Collectors.toList());
    }
}
