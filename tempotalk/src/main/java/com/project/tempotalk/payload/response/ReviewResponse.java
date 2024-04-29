package com.project.tempotalk.payload.response;

import com.project.tempotalk.models.Review;
import lombok.Data;

@Data
public class ReviewResponse {
    private Review review = null;
    private String message;

    public ReviewResponse(Review review, String message){
        this.review = review;
        this.message = message;
    }

    public ReviewResponse(String message){
        this.message = message;
    }
}
