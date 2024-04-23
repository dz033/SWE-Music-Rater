package com.project.tempotalk.services;

import com.project.tempotalk.models.User;
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
}
