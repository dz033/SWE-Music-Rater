package com.project.tempotalk.payload.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImageUploadResponse {
    private String filePath;
    private LocalDateTime dateTime;
}
