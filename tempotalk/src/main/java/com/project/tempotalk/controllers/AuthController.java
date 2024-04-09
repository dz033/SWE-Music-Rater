package com.project.tempotalk.controllers;

import com.project.tempotalk.payload.request.LoginRequest;
import com.project.tempotalk.payload.request.SignupRequest;
import com.project.tempotalk.payload.response.JwtResponse;
import com.project.tempotalk.payload.response.MessageResponse;
import com.project.tempotalk.services.AuthService;
import jakarta.validation.Valid;
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
