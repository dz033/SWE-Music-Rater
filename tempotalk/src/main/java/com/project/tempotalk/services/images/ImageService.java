package com.project.tempotalk.services.images;

import com.project.tempotalk.payload.response.ImageUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImageUploadResponse uploadImage(MultipartFile multipartFile);
}
