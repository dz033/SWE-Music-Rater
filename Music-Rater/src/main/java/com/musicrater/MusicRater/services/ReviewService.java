package com.musicrater.MusicRater.services;

import com.musicrater.MusicRater.repositories.ReviewRepository;
import com.musicrater.MusicRater.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    public Review createReview(Review review){
        reviewRepository.insert(review);

//        mongoTemplate.update(Album.class)
//                .matching(Criteria.where("id").is(id))
//                .apply(new Update().push("reviews").value(review))
//                .first();

        return review;
    }
}
