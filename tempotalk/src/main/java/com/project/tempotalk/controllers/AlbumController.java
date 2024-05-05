package com.project.tempotalk.controllers;

import com.project.tempotalk.models.Album;
import com.project.tempotalk.payload.request.AlbumRequest;
import com.project.tempotalk.payload.response.AlbumResponse;
import com.project.tempotalk.services.AlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/albums")
public class AlbumController {
    @Autowired
    AlbumService albumService;

    @GetMapping()
    public ResponseEntity<List<Album>> getAllAlbums(){
        return new ResponseEntity<>(albumService.allAlbums(), HttpStatus.OK);
    }

    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumResponse> getAlbumById(@PathVariable String albumId){
        AlbumResponse response = albumService.albumById(albumId);

        if (response.getAlbum() == null){
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Album>> getAlbumsByName(@PathVariable String title){
        return new ResponseEntity<>(albumService.albumsByTitle(title), HttpStatus.OK);
    }

    @GetMapping("/discovery")
    public ResponseEntity<List<Album>> getDiscoveryAlbums(){
        return new ResponseEntity<>(albumService.getRandomAlbums(21), HttpStatus.OK);
    }

    @GetMapping("/newReleases")
    public ResponseEntity<List<Album>> getNewReleases(){
        return new ResponseEntity<>(albumService.getNewAlbums(21), HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AlbumResponse> createAlbum(@Valid @RequestBody AlbumRequest albumRequest){
        return new ResponseEntity<>(albumService.createAlbum(albumRequest), HttpStatus.OK);
    }
}
