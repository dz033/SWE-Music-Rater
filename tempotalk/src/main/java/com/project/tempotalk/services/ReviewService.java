package com.project.tempotalk.services;

import com.project.tempotalk.models.Review;
import com.project.tempotalk.payload.request.ReviewRequest;
import com.project.tempotalk.payload.response.MessageResponse;
import com.project.tempotalk.repositories.ReviewRepository;
import com.project.tempotalk.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    public List<Review> allReviews(){
        return reviewRepository.findAll();
    }

    public MessageResponse createReview(ReviewRequest reviewRequest){
        if (!userRepository.existsByUsername(reviewRequest.getUsername())){
            return new MessageResponse("Error: Username not found");
        }

        // Create a new review object
        Review review = new Review(reviewRequest.getBody(), reviewRequest.getRating());

        reviewRepository.save(review);
        return new MessageResponse("Review created successfully!");
    }
}
