package com.musicrater.MusicRater.controllers;

import com.musicrater.MusicRater.payload.request.LoginRequest;
import com.musicrater.MusicRater.payload.request.SignupRequest;
import com.musicrater.MusicRater.payload.response.JwtResponse;
import com.musicrater.MusicRater.payload.response.MessageResponse;
import com.musicrater.MusicRater.services.AuthService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest){
        return new ResponseEntity<JwtResponse>(authService.authenticateUser(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signupRequest){
        MessageResponse response = authService.registerUser(signupRequest);

        if (!response.getMessage().equals("User registered successfully!")){
            return new ResponseEntity<MessageResponse>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<MessageResponse>(response, HttpStatus.OK);
    }
}
