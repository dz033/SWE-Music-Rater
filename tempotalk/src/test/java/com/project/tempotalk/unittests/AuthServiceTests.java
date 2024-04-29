package com.project.tempotalk.unittests;

import com.project.tempotalk.models.User;
import com.project.tempotalk.payload.request.SignupRequest;
import com.project.tempotalk.payload.response.MessageResponse;
import com.project.tempotalk.repositories.UserRepository;
import com.project.tempotalk.services.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//David
@ExtendWith(MockitoExtension.class)
public class AuthServiceTests{

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void duplicateUsernameError(){
        Set<String> roles = new HashSet<>();
        roles.add("user");
        SignupRequest request = new SignupRequest("bobjoe", "bobjoe@gmail.com", roles, "password");
        when(userRepository.existsByUsername(Mockito.any(String.class))).thenReturn(true);
        MessageResponse response = authService.registerUser(request);
        assertEquals("Error: Username is already taken!", response.getMessage());
    }

    @Test
    void duplicateEmailError(){
        Set<String> roles = new HashSet<>();
        roles.add("user");
        SignupRequest request = new SignupRequest("bobjoe", "bobjoe@gmail.com", roles, "password");
        when(userRepository.existsByEmail(Mockito.any(String.class))).thenReturn(true);
        MessageResponse response = authService.registerUser(request);
        assertEquals("Error: Email is already in use!", response.getMessage());
    }
    
//    @Test
//    void rolesSpecifiedUser(){
//        Set<String> roles = new HashSet<>();
//        roles.add("user");
//        SignupRequest request = new SignupRequest("bobjoe", "bobjoe@gmail.com", roles, "password");
//        when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
//        MessageResponse response = authService.registerUser(request);
//        assertEquals("User registered successfully!", response.getMessage());
//    }
//
//    @Test
//    void rolesSpecifiedAdmin(){
//        Set<String> roles = new HashSet<>();
//        roles.add("admin");
//        SignupRequest request = new SignupRequest("bobjoe", "bobjoe@gmail.com", roles, "password");
//        when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
//        Mockito.when(userService.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
//        MessageResponse response = authService.registerUser(request);
//        assertEquals("User registered successfully!", response.getMessage());
//    }
//
//    @Test
//    void rolesUnspecified(){
//        Set<String> roles = new HashSet<>();
//        SignupRequest request = new SignupRequest("bobjoe", "bobjoe@gmail.com", roles, "password");
//        when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
//        MessageResponse response = authService.registerUser(request);
//        assertEquals("User registered successfully!", response.getMessage());
//    }
}