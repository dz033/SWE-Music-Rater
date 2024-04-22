package com.project.tempotalk.controllers;

import com.project.tempotalk.models.Album;
import com.project.tempotalk.payload.request.AlbumRequest;
import com.project.tempotalk.payload.response.MessageResponse;
import com.project.tempotalk.services.AlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {
    @Autowired
    AlbumService albumService;

    @GetMapping()
    public ResponseEntity<List<Album>> getAllAlbums(){
        return new ResponseEntity<List<Album>>(albumService.allAlbums(), HttpStatus.OK);
    }

    @GetMapping("/{title}")
    public ResponseEntity<Optional<List<Album>>> getSingleAlbum(@PathVariable String title){
        return new ResponseEntity<Optional<List<Album>>>(albumService.albumsByTitle(title), HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> createAlbum(@Valid @RequestBody AlbumRequest albumRequest){
        MessageResponse response = albumService.createAlbum(albumRequest);

        if (!response.getMessage().equals("Album created successfully!")){
            return new ResponseEntity<MessageResponse>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<MessageResponse>(response, HttpStatus.OK);
    }
}
