package com.project.tempotalk.controllers;

import com.project.tempotalk.models.Album;
import com.project.tempotalk.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @GetMapping()
    public ResponseEntity<List<Album>> getAllAlbums(){
        return new ResponseEntity<List<Album>>(albumService.allAlbums(), HttpStatus.OK);
    }

    @GetMapping("/{title}")
    public ResponseEntity<Optional<List<Album>>> getSingleAlbum(@PathVariable String title){
        return new ResponseEntity<Optional<List<Album>>>(albumService.albumsByTitle(title), HttpStatus.OK);
    }
}
