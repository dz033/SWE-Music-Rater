package com.project.tempotalk.unittests;

import com.project.tempotalk.models.Album;
import com.project.tempotalk.models.Review;
import com.project.tempotalk.models.User;
import com.project.tempotalk.payload.request.ReviewRequest;
import com.project.tempotalk.payload.response.ReviewResponse;
import com.project.tempotalk.repositories.AlbumRepository;
import com.project.tempotalk.repositories.ReviewRepository;
import com.project.tempotalk.repositories.SongRepository;
import com.project.tempotalk.repositories.UserRepository;
import com.project.tempotalk.services.ReviewService;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.LENIENT);

    @Mock
    UserRepository userRepository;

    @Mock
    AlbumRepository albumRepository;

    @Mock
    SongRepository songRepository;

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    List<String> musicReviews;

    @Mock
    List<String> userReviews;

    @InjectMocks
    ReviewService reviewService;

    private Album album;
    private User user;
    private Review review;

    @BeforeEach
    public void init(){
        album = new Album("Wall of Eyes", "The Smile", "12-01-01", new ArrayList<>(Arrays.asList("Alternative Rock")));
        user = new User("user", "temp@gmail.com", "password");
        review = new Review("body", 100, "userId", "musicId");
        userReviews = new ArrayList<>(Arrays.asList("reviewId1", "reviewId2"));
    }


    @Test
    public void ReviewService_CreateReview_UserNotFoundResponse(){
        ReviewRequest reviewRequest = new ReviewRequest("body", 100, "userId", "musicId");
        when(userRepository.findById(reviewRequest.getUserId())).thenReturn(Optional.empty());
        ReviewResponse response = reviewService.createReview(reviewRequest);
        assertThat(response.getReview()).isNull();
        assertThat(response.getMessage()).isEqualTo("Error: User not found");
    }

    @Test
    public void ReviewService_CreateReview_MusicNotFoundResponse(){
        ReviewRequest reviewRequest = new ReviewRequest("body", 100, "userId", "musicId");
        when(userRepository.findById(reviewRequest.getUserId())).thenReturn(Optional.of(new User()));
        when(albumRepository.existsById(reviewRequest.getMusicId())).thenReturn(false);
        when(songRepository.existsById(reviewRequest.getMusicId())).thenReturn(false);
        ReviewResponse response = reviewService.createReview(reviewRequest);
        assertThat(response.getReview()).isNull();
        assertThat(response.getMessage()).isEqualTo("Error: no album or song was found");
    }

    @Test
    public void ReviewService_CreateReview_ReviewSuccessfullyCreatedResponse(){
        ReviewRequest reviewRequest = new ReviewRequest("body", 100, "userId", "musicId");
        album.setReviews(new ArrayList<>(List.of("reviewId1")));
        user.setReviews(userReviews);
        when(userRepository.findById(reviewRequest.getUserId())).thenReturn(Optional.of(user));
        when(albumRepository.existsById(reviewRequest.getMusicId())).thenReturn(true);
        when(albumRepository.findById(reviewRequest.getMusicId())).thenReturn(Optional.of(album));
        ReviewResponse response = reviewService.createReview(reviewRequest);
        assertThat(response.getReview()).isNull();
        assertThat(response.getMessage()).isEqualTo("Error: User has already created a review for this music");
    }

    @Test
    public void ReviewService_CreateReview_ReviewAlreadyCreatedResponse (){
        ReviewRequest reviewRequest = new ReviewRequest("body", 100, "userId", "musicId");
        album.setReviews(new ArrayList<>(List.of("reviewId")));
        user.setReviews(userReviews);
        when(userRepository.findById(reviewRequest.getUserId())).thenReturn(Optional.of(user));
        when(albumRepository.existsById(reviewRequest.getMusicId())).thenReturn(true);
        when(albumRepository.findById(reviewRequest.getMusicId())).thenReturn(Optional.of(album));
        when(reviewRepository.findById("reviewId")).thenReturn(Optional.of(review));
        ReviewResponse response = reviewService.createReview(reviewRequest);
        assertThat(response.getReview()).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Review created successfully!");
    }
}