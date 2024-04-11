package com.project.tempotalk.services;

import com.project.tempotalk.models.Review;
import com.project.tempotalk.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    public List<Review> allReviews(){
        return reviewRepository.findAll();
    }
}
