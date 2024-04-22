package com.project.tempotalk.controllers;

import com.project.tempotalk.payload.request.ArtistRequest;
import com.project.tempotalk.payload.response.MessageResponse;
import com.project.tempotalk.services.ArtistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {
    @Autowired
    ArtistService artistService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> createArtist(@Valid @RequestBody ArtistRequest artistRequest){
        MessageResponse response = artistService.createArtist(artistRequest);

        if (!response.getMessage().equals("Artist created successfully!")){
            return new ResponseEntity<MessageResponse>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<MessageResponse>(response, HttpStatus.OK);
    }
}
