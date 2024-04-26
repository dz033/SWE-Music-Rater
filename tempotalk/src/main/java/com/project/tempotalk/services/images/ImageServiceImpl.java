package com.project.tempotalk.services.images;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.project.tempotalk.exceptions.FileUploadException;
import com.project.tempotalk.payload.response.ImageUploadResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService{
    @Value("${aws.s3.bucketName}")
    private String bucketName;
    @Value("${aws.s3.accessKey}")
    private String accessKey;
    @Value("${aws.s3.secretKey}")
    private String secretKey;

    private AmazonS3 s3client;

    @PostConstruct
    private void initialize(){
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }

    @Override
    public ImageUploadResponse uploadImage(MultipartFile multipartFile){
        ImageUploadResponse imageUploadResponse = new ImageUploadResponse();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = dateTimeFormatter.format(LocalDate.now());
        String filePath = "";
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getSize());
            filePath = todayDate + "/" + multipartFile.getOriginalFilename();
            s3client.putObject(bucketName, filePath, multipartFile.getInputStream(), objectMetadata);
            imageUploadResponse.setFilePath(filePath);
            imageUploadResponse.setDateTime(LocalDateTime.now());
        } catch (IOException e){
            log.error("Error occurred ==> {}", e.getMessage());
            throw new FileUploadException("Error occurred in file upload ==> " + e.getMessage());
        }
        return imageUploadResponse;
    }
}
