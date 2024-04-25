package com.project.tempotalk.controllers;

import com.project.tempotalk.models.Review;
import com.project.tempotalk.payload.request.EditReviewRequest;
import com.project.tempotalk.payload.request.ReviewRequest;
import com.project.tempotalk.payload.response.MessageResponse;
import com.project.tempotalk.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(){
        return new ResponseEntity<>(reviewService.allReviews(), HttpStatus.OK);
    }

    @GetMapping("/{musicId}")
    public ResponseEntity<Optional<List<Review>>> getReviewsByMusicId(@PathVariable String musicId){
        return new ResponseEntity<>(reviewService.getReviewsByMusicId(musicId), HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewRequest reviewRequest){
        MessageResponse response = reviewService.createReview(reviewRequest);

        if (!response.getMessage().equals("Review created successfully!")){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> editReview(@Valid @RequestBody EditReviewRequest editReviewRequest){
        MessageResponse response = reviewService.updateReview(editReviewRequest);

        if (!response.getMessage().equals("Review updated successfully!")){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}