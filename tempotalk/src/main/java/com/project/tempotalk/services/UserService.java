package com.project.tempotalk.services;

import com.project.tempotalk.models.User;
import com.project.tempotalk.payload.request.FollowRequest;
import com.project.tempotalk.payload.response.MessageResponse;
import com.project.tempotalk.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    // Return a list of all users in userRepository
    public List<User> allUsers(){
        return userRepository.findAll();
    }

    // Return a User object by their username, if they exist
    public Optional<User> userByUsername(String username){
        return userRepository.findByUsername(username);
    }

    // Follow a user and update follower list
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
            following.add(followRequest.getFolloweeId());
            follower.setFollowing(following);
            userRepository.save(follower);
        }

        return new MessageResponse("User followed successfully!");
    }
}
