package com.musicrater.MusicRater.unittests;

import com.musicrater.MusicRater.payload.request.LoginRequest;
import com.musicrater.MusicRater.payload.request.SignupRequest;
import com.musicrater.MusicRater.payload.response.MessageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@Service
public class AuthTests {
    AuthControllerMock authControllerMock = new AuthControllerMock();

    // Emilio
    @Test
    void testLogin(){
        LoginRequest loginRequest = new LoginRequest("username", "password");
        ResponseEntity<?> response = authControllerMock.authenticate(loginRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    // Emilio
    @Test
    void testInvalidRegistration(){
        Set<String> roles = new HashSet<>();
        roles.add("User");
        SignupRequest request = new SignupRequest("username", "temp@gmail.com", roles, "password");
        MessageResponse message = new MessageResponse("Error");
        ResponseEntity<?> response = authControllerMock.register(request, message);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
    }

    // Emilio
    @Test
    void validRegistration(){
        Set<String> roles = new HashSet<>();
        roles.add("User");
        SignupRequest request = new SignupRequest("username", "temp@gmail.com", roles, "password");
        MessageResponse message = new MessageResponse("User registered successfully");
        ResponseEntity<?> response = authControllerMock.register(request, message);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}