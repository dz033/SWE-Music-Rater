package com.project.tempotalk.services;

import com.project.tempotalk.models.Album;
import com.project.tempotalk.models.Review;
import com.project.tempotalk.models.Song;
import com.project.tempotalk.models.User;
import com.project.tempotalk.payload.request.EditReviewRequest;
import com.project.tempotalk.payload.request.ReviewRequest;
import com.project.tempotalk.payload.response.MessageResponse;
import com.project.tempotalk.payload.response.ReviewResponse;
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

    // Return all reviews associated with an album or song
    public Optional<List<Review>> getReviewsByMusicId(String musicId){
        return reviewRepository.findReviewsByMusicId(musicId);
    }

    // Return all reviews associated with a user
    public Optional<List<Review>> getReviewsByUserId(String userId){
        return reviewRepository.findReviewsByUserId(userId);
    }

    // Create a review and add its ID to a User's and Album/Song's review lists
    public ReviewResponse createReview(ReviewRequest reviewRequest){
        // Check to make sure user specified in the review request exist
        Optional<User> tempUser = userRepository.findById(reviewRequest.getUserId());
        if (tempUser.isEmpty()){
            return new ReviewResponse("Error: User not found");
        }
        User user = tempUser.get();
        List<String> userReviews = user.getReviews();
        List<String> musicReviews;
        Album album = null;
        Song song = null;

        // Make sure that the album/song being reviewed exists
        if (albumRepository.existsById(reviewRequest.getMusicId())){
            Optional<Album> tempAlbum = albumRepository.findById(reviewRequest.getMusicId());
            if (tempAlbum.isEmpty()){
                return new ReviewResponse("Error: Album not found");
            }
            album = tempAlbum.get();
            musicReviews = album.getReviews();
        }
        else if (songRepository.existsById(reviewRequest.getMusicId())){
            Optional<Song> tempSong = songRepository.findById(reviewRequest.getMusicId());
            if (tempSong.isEmpty()){
                return new ReviewResponse("Error: Song not found");
            }
            song = tempSong.get();
            musicReviews = song.getReviews();
        }else{
            return new ReviewResponse("Error: no album or song was found");
        }

        // Check to make sure the user hasn't already created a review for this album/song
        for (String id : userReviews){
            if (musicReviews.contains(id)){
                return new ReviewResponse("Error: User has already created a review for this music");
            }
        }

        // Create a new review object
        Review review = new Review(reviewRequest.getBody(), reviewRequest.getRating(), reviewRequest.getUserId(), reviewRequest.getMusicId());
        reviewRepository.save(review);

        // Find user who made the review and add the new review ID to their list of reviews
        userReviews.add(review.getId());
        user.setReviews(userReviews);
        userRepository.save(user);

        // Find the album or song associated with the review and add the new review ID to their list of reviews
        if (album != null){
            musicReviews.add(review.getId());
            album.setReviews(musicReviews);

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
        else {
            musicReviews.add(review.getId());
            song.setReviews(musicReviews);

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

        return new ReviewResponse(review,"Review created successfully!");
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

    // Delete a review, remove its ID from the associated User and Album/Song's reviews list, and recalculate Album/Song score
    public MessageResponse deleteReview(String reviewId){
        // Make sure that the review exists
        if (!reviewRepository.existsById(reviewId)){
            return new MessageResponse("Error: Review was not found");
        }

        // Delete all traces of the review we are deleting
        Optional<Review> tempReview = reviewRepository.findById(reviewId);
        if (tempReview.isPresent()){
            Review review = tempReview.get();
            String userId = review.getUserId();
            String musicId = review.getMusicId();

            // Remove the reviewId from associated User's reviews list
            Optional<User> tempUser = userRepository.findById(userId);
            if (tempUser.isPresent()){
                User user = tempUser.get();
                List<String> reviews = user.getReviews();
                reviews.remove(reviewId);
                user.setReviews(reviews);
                userRepository.save(user);
            }
            else{
                return new MessageResponse("Error: Review was not associated with a user");
            }

            // Remove the reviewId from associated Album/Song's reviews list
            if (albumRepository.existsById(musicId)){
                Optional<Album> tempAlbum = albumRepository.findById(musicId);
                if (tempAlbum.isPresent()){
                    Album album = tempAlbum.get();
                    List<String> reviews = album.getReviews();
                    reviews.remove(reviewId);
                    album.setReviews(reviews);

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
            else if (songRepository.existsById(musicId)){
                Optional<Song> tempSong = songRepository.findById(musicId);
                if (tempSong.isPresent()){
                    Song song = tempSong.get();
                    List<String> reviews = song.getReviews();
                    reviews.remove(reviewId);
                    song.setReviews(reviews);

                    // Update album score
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

            reviewRepository.deleteById(reviewId);
        }
        else{
            return new MessageResponse("Error: Review was not found");
        }

        return new MessageResponse("Review deleted successfully!");
    }
}