package com.project.tempotalk.controllers;

import com.project.tempotalk.models.Playlist;
import com.project.tempotalk.payload.request.PlaylistRequest;
import com.project.tempotalk.payload.response.PlaylistResponse;
import com.project.tempotalk.services.PlaylistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    @Autowired
    PlaylistService playlistService;

    @GetMapping()
    public ResponseEntity<List<Playlist>> getAllPlaylists(){
        return new ResponseEntity<>(playlistService.allPlaylists(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Optional<List<Playlist>>> getPlaylistByOwnerId(@PathVariable String userId){
        return new ResponseEntity<>(playlistService.getPlaylistsByUserId(userId), HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PlaylistResponse> createPlaylist(@Valid @RequestBody PlaylistRequest playlistRequest){
        PlaylistResponse response = playlistService.createPlaylist(playlistRequest);

        if (!response.getMessage().equals("Playlist created successfully!")){
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}