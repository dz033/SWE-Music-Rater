package com.project.tempotalk.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class ImageUploadRequest {
    private MultipartFile file;
    private String id;
}
