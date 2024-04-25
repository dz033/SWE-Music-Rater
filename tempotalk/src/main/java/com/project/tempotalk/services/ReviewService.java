package com.project.tempotalk.services;

import com.project.tempotalk.models.Album;
import com.project.tempotalk.models.Review;
import com.project.tempotalk.models.Song;
import com.project.tempotalk.models.User;
import com.project.tempotalk.payload.request.EditReviewRequest;
import com.project.tempotalk.payload.request.ReviewRequest;
import com.project.tempotalk.payload.response.MessageResponse;
import com.project.tempotalk.repositories.AlbumRepository;
import com.project.tempotalk.repositories.ReviewRepository;
import com.project.tempotalk.repositories.SongRepository;
import com.project.tempotalk.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    SongRepository songRepository;

    // Return all reviews in reviewRepository
    public List<Review> allReviews(){
        return reviewRepository.findAll();
    }

    // Return all reviews associated an album
    public Optional<List<Review>> getReviewsByMusicId(String musicId){
        return reviewRepository.findReviewsByMusicId(musicId);
    }

    // Create a review and add its ID to a User's and Album/Song's review lists
    public MessageResponse createReview(ReviewRequest reviewRequest){
        // Check to make sure both user and album/song specified in the review request exist
        if (!userRepository.existsById(reviewRequest.getUserId())){
            return new MessageResponse("Error: User not found");
        }
        else if (!(albumRepository.existsById(reviewRequest.getMusicId()) || songRepository.existsById(reviewRequest.getMusicId()))){
            return new MessageResponse("Error: Album or song not found");
        }

        // Create a new review object
        Review review = new Review(reviewRequest.getBody(), reviewRequest.getRating(), reviewRequest.getUserId(), reviewRequest.getMusicId());
        reviewRepository.save(review);

        // Find user who made the review and add the new review ID to their list of reviews
        Optional<User> user = userRepository.findById(reviewRequest.getUserId());
        if (user.isPresent()){
            User user1 = user.get();
            List<String> userReviews = user1.getReviews();
            userReviews.add(review.getId());
            user1.setReviews(userReviews);
            userRepository.save(user1);
        }
        else{
            return new MessageResponse("Error: Review was not associated with a user");
        }

        // Find the album or song associated with the review and add the new review ID to their list of reviews
        if (albumRepository.existsById(reviewRequest.getMusicId())){
            Optional<Album> album = albumRepository.findById(reviewRequest.getMusicId());
            if (album.isPresent()){
                // Update review list in album
                Album album1 = album.get();
                List<String> albumReviews = album1.getReviews();
                albumReviews.add(review.getId());
                album1.setReviews(albumReviews);

                // Update album score
                List<Integer> scores = new ArrayList<>();
                for (String id : album1.getReviews()){
                    Optional<Review> r = reviewRepository.findById(id);
                    if (r.isPresent()){
                        Review curReview = r.get();
                        scores.add(curReview.getScore());
                    }
                }
                album1.calculateScore(scores);

                albumRepository.save(album1);
            }
            else{
                return new MessageResponse("Error: Review was not associated with an album");
            }
        }
        else if (songRepository.existsById(reviewRequest.getMusicId())){
            Optional<Song> song = songRepository.findById(reviewRequest.getMusicId());
            if (song.isPresent()){
                Song song1 = song.get();
                List<String> songReviews = song1.getReviews();
                songReviews.add(review.getId());
                song1.setReviews(songReviews);

                // Update song score
                List<Integer> scores = new ArrayList<>();
                for (String id : song1.getReviews()){
                    Optional<Review> r = reviewRepository.findById(id);
                    if (r.isPresent()){
                        Review curReview = r.get();
                        scores.add(curReview.getScore());
                    }
                }
                song1.calculateScore(scores);

                songRepository.save(song1);
            }
            else{
                return new MessageResponse("Error: Review was not associated with an album or song");
            }
        }

        return new MessageResponse("Review created successfully!");
    }

    // Update a review and update the score of the Album/Song it is associated with
    public MessageResponse updateReview(EditReviewRequest editReviewRequest){
        // Check to make sure both the review and the album/song specified in the request exist
        if (!reviewRepository.existsById(editReviewRequest.getReviewId())){
            return new MessageResponse("Error: Review was not found");
        }
        else if (!(albumRepository.existsById(editReviewRequest.getMusicId()) || songRepository.existsById(editReviewRequest.getMusicId()))){
            return new MessageResponse("Error: Album or song was not found");
        }

        // Find user who made the review and add the new review ID to their list of reviews
        Optional<Review> tempReview = reviewRepository.findById(editReviewRequest.getReviewId());
        if (tempReview.isPresent()){
            Review review = tempReview.get();
            review.setBody(editReviewRequest.getBody());
            review.setScore(editReviewRequest.getRating());
            reviewRepository.save(review);
        }
        else{
            return new MessageResponse("Error: Review was not found");
        }

        // Find Album or Song that the updated review was associated with and recalculate its score
        if (albumRepository.existsById(editReviewRequest.getMusicId())){
            Optional<Album> tempAlbum = albumRepository.findById(editReviewRequest.getMusicId());
            if (tempAlbum.isPresent()){
                Album album = tempAlbum.get();

                // Update album score
                List<Integer> scores = new ArrayList<>();
                for (String id : album.getReviews()){
                    Optional<Review> r = reviewRepository.findById(id);
                    if (r.isPresent()){
                        Review curReview = r.get();
                        scores.add(curReview.getScore());
                    }
                }
                album.calculateScore(scores);

                albumRepository.save(album);
            }
        }
        else if (songRepository.existsById(editReviewRequest.getMusicId())){
            Optional<Song> tempSong = songRepository.findById(editReviewRequest.getMusicId());
            if (tempSong.isPresent()){
                Song song = tempSong.get();

                // Update song score
                List<Integer> scores = new ArrayList<>();
                for (String id : song.getReviews()){
                    Optional<Review> r = reviewRepository.findById(id);
                    if (r.isPresent()){
                        Review curReview = r.get();
                        scores.add(curReview.getScore());
                    }
                }
                song.calculateScore(scores);

                songRepository.save(song);
            }
        }
        else{
            return new MessageResponse("Error: Review was not associated with an album or song");
        }

        return new MessageResponse("Review updated successfully!");
    }
}
