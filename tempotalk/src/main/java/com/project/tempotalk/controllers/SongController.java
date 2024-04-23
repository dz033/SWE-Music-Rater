package com.project.tempotalk.controllers;

import com.project.tempotalk.models.Song;
import com.project.tempotalk.payload.request.SongRequest;
import com.project.tempotalk.payload.response.MessageResponse;
import com.project.tempotalk.services.SongService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/songs")
public class SongController {
    @Autowired
    SongService songService;

    @GetMapping()
    public ResponseEntity<List<Song>> getAllSongs(){
        return new ResponseEntity<>(songService.allSongs(), HttpStatus.OK);
    }

    @GetMapping("/{title}")
    public ResponseEntity<Optional<List<Song>>> getSongsByTitle(@PathVariable String title){
        return new ResponseEntity<>(songService.songsByTitle(title), HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> createSong(@Valid @RequestBody SongRequest songRequest){
        MessageResponse response = songService.createSong(songRequest);

        if (!response.getMessage().equals("Song created successfully!")){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
