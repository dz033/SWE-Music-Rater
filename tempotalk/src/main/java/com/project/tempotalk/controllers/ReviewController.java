package com.project.tempotalk.controllers;

import com.project.tempotalk.models.Review;
import com.project.tempotalk.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

//    @GetMapping
//    public String test(){
//        return "This API is reachable!";
//    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(){
        return new ResponseEntity<List<Review>>(reviewService.allReviews(), HttpStatus.OK);
    }
}