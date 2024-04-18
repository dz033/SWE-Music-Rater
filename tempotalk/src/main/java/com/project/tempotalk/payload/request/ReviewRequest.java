package com.project.tempotalk.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewRequest {
    private String body;
    private int rating;
    @NotBlank
    private String username;
}
