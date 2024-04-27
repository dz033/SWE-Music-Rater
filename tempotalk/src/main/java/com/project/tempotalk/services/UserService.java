package com.project.tempotalk.services;

import com.project.tempotalk.models.Review;
import com.project.tempotalk.models.User;
import com.project.tempotalk.payload.request.FollowRequest;
import com.project.tempotalk.payload.response.MessageResponse;
import com.project.tempotalk.repositories.ReviewRepository;
import com.project.tempotalk.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;

    // Return a list of all users in userRepository
    public List<User> allUsers(){
        return userRepository.findAll();
    }

    // Return a User object by their username, if they exist
    public Optional<User> userByUsername(String username){
        return userRepository.findByUsername(username);
    }

    // Follow a user and update following list
    public MessageResponse followUser(FollowRequest followRequest){
        if (!userRepository.existsById(followRequest.getFolloweeId())){
            return new MessageResponse("Error: The user being followed was not found");
        }
        if (!userRepository.existsById(followRequest.getFollowerId())){
            return new MessageResponse("Error: The following user was not found");
        }

        Optional<User> user = userRepository.findById(followRequest.getFollowerId());
        if (user.isPresent()){
            User follower = user.get();
            List<String> following = follower.getFollowing();

            // Make sure the follower isn't already following the followee
            if (following.contains(followRequest.getFolloweeId())){
                return new MessageResponse("Error: User is already being followed");
            }

            following.add(followRequest.getFolloweeId());
            follower.setFollowing(following);
            userRepository.save(follower);
        }

        return new MessageResponse("User followed successfully!");
    }

    // Unfollow a user and update following list
    public MessageResponse unfollowUser(FollowRequest followRequest){
        if (!userRepository.existsById(followRequest.getFolloweeId())){
            return new MessageResponse("Error: The user being unfollowed was not found");
        }
        if (!userRepository.existsById(followRequest.getFollowerId())){
            return new MessageResponse("Error: The unfollowing user was not found");
        }

        Optional<User> user = userRepository.findById(followRequest.getFollowerId());
        if (user.isPresent()){
            User follower = user.get();
            List<String> following = follower.getFollowing();

            // Remove user from following list, update the User object, and save it in the database
            following.remove(followRequest.getFolloweeId());
            follower.setFollowing(following);
            userRepository.save(follower);
        }

        return new MessageResponse("User unfollowed successfully!");
    }

    // Get followed users
    public List<User> getFollowedUsers(String userId){
        List<User> followedUsers = new ArrayList<>();
        Optional<User> tempUser = userRepository.findById(userId);
        if (tempUser.isPresent()){
            User user = tempUser.get();
            List<String> followingIds = user.getFollowing();


            for (String id : followingIds){
                Optional<User> curUser = userRepository.findById(id);
                if (curUser.isPresent()){
                    followedUsers.add(curUser.get());
                }
            }
        }

        return followedUsers;
    }

    // Get a feed of reviews from other users that the requesting user follows
    public List<Review> getUserFeed(String userId){
        List<Review> feed = new ArrayList<>();

        Optional<User> tempUser = userRepository.findById(userId);
        if (tempUser.isPresent()){
            List<String> following = tempUser.get().getFollowing();

            // Create a new Criteria object and search for reviews with userId in following
            Criteria criteria = new Criteria();
            criteria.and("userId").in(following);

            // Create a new Query object and sort by creation date (newest to oldest)
            Query query = new Query(criteria);
            query.with(Sort.by(new Sort.Order(Sort.Direction.DESC, "creationDate")));
            feed = mongoTemplate.find(query, Review.class);
        }

        return feed;
    }
}
