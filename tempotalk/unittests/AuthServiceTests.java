package com.musicrater.MusicRater.unittests;

import com.musicrater.MusicRater.payload.request.LoginRequest;
import com.musicrater.MusicRater.payload.request.SignupRequest;
import com.musicrater.MusicRater.payload.response.MessageResponse;
import com.musicrater.MusicRater.repositories.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.Before;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;




//David
@Service
public class AuthServiceTests{
    private AuthService authService;
    private UserRepository userRepositoryMock;

    @Before
    void setup(){
        userRepositoryMock = mock(UserRepository.class);
        authService = new AuthService(userRepositoryMock);
    }

    @Test
    void duplicateUsernameError(){
        
        Set<String> roles = new HashSet<>();
        roles.add("User");
        SignupRequest request = new SignupRequest("bobjoe", "bobjoe@gmail.com", roles, "password");
        when(userRepositoryMock.existsByUsername()).thenReturn(true);
        MessageResponse response = authService.registerUser(request);
        assertEquals("Error: Username is already taken!", response.getMessage());
    }

    @Test
    void duplicateEmailError(){
        Set<String> roles = new HashSet<>();
        roles.add("User");
        SignupRequest request = new SignupRequest("bobjoe", "bobjoe@gmail.com", roles, "password");
        when(userRepositoryMock.existsByEmail()).thenReturn(true);
        MessageResponse response = authService.registerUser(request);
        assertEquals("Error: Email is already in use!", response.getMessage());
    }
    
    @Test
    void rolesSpecifiedUser(){
        Set<String> roles = new HashSet<>();
        roles.add("User");
        SignupRequest request = new SignupRequest("bobjoe", "bobjoe@gmail.com", roles, "password");
        MessageResponse response = authService.registerUser(request);
        assertEquals("User registered successfully!", response.getMessage());
        
    }

    @Test
    void rolesSpecifiedAdmin(){
        Set<String> roles = new HashSet<>();
        roles.add("Admin");
        SignupRequest request = new SignupRequest("bobjoe", "bobjoe@gmail.com", roles, "password");
        MessageResponse response = authService.registerUser(request);
        assertEquals("User registered successfully!", response.getMessage());
        
    }

    @Test
    void rolesUnspecified(){
        Set<String> roles = new HashSet<>();
        SignupRequest request = new SignupRequest("bobjoe", "bobjoe@gmail.com", roles, "password");
        MessageResponse response = authService.registerUser(request);
        assertEquals("User registered successfully!", response.getMessage());
        
    }
}