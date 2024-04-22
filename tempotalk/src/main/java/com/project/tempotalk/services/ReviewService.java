package com.project.tempotalk.services;

import com.project.tempotalk.models.Review;
import com.project.tempotalk.models.User;
import com.project.tempotalk.payload.request.ReviewRequest;
import com.project.tempotalk.payload.response.MessageResponse;
import com.project.tempotalk.repositories.ReviewRepository;
import com.project.tempotalk.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        if (!userRepository.existsById(reviewRequest.getUserId())){
            return new MessageResponse("Error: User not found");
        }

        // Create a new review object
        Review review = new Review(reviewRequest.getBody(), reviewRequest.getRating(), reviewRequest.getUserId(), reviewRequest.getMusicId());
        reviewRepository.save(review);

        // Find user who made the review and add the new review ID to their list of reviews
        Optional<User> user = userRepository.findById(reviewRequest.getUserId());
        if (user.isPresent()){
            User user1 = user.get();
            List<String> list = user1.getReviews();
            System.out.println(review.getId());
            list.add(review.getId());
            user1.setReviews(list);
            userRepository.save(user1);
        }

        return new MessageResponse("Review created successfully!");
    }
}
