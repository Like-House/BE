package backend.like_house.global.s3.service;

import backend.like_house.global.s3.dto.AwsDTO;
import backend.like_house.global.s3.dto.AwsDTO.PresignedUrlDownLoadResponse;
import backend.like_house.global.s3.dto.AwsDTO.PresignedUrlUploadResponse;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public PresignedUrlUploadResponse getPresignedUrlToUpload(String fileName) {
        /// 제한시간 설정
        Date expiration = new Date();
        long expTime = expiration.getTime();
        expTime += TimeUnit.MINUTES.toMillis(3); // 3 Minute
        expiration.setTime(expTime);

        String keyName = UUID.randomUUID() + "_" + fileName;

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, keyName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        String key = generatePresignedUrlRequest.getKey();

        return PresignedUrlUploadResponse.builder()
                .url(amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString())
                .keyName(key)
                .build();

    }

    public PresignedUrlDownLoadResponse getPresignedUrlToDownload(String keyName) {
        /// 제한시간 설정
        Date expiration = new Date();
        long expTime = expiration.getTime();
        expTime += TimeUnit.MINUTES.toMillis(3);
        expiration.setTime(expTime); // 3 Minute

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, keyName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        return PresignedUrlDownLoadResponse.builder()
                .url(amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString())
                .build();
    }

    public Boolean isExistKeyName(String keyName) {
        return amazonS3.doesObjectExist(bucket, keyName);
    }

    public void uploadFile(AwsDTO.FileUploadRequest uploadRequest) {
        String fileName = uploadRequest.getFileName();
        byte[] fileData = uploadRequest.getFileData();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileData.length);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, inputStream, metadata);

        amazonS3.putObject(putObjectRequest);

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
