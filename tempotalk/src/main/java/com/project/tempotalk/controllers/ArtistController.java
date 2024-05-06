package com.project.tempotalk.controllers;

import com.project.tempotalk.models.Artist;
import com.project.tempotalk.payload.request.ArtistRequest;
import com.project.tempotalk.payload.response.ArtistResponse;
import com.project.tempotalk.services.ArtistService;
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
@RequestMapping("/api/artists")
public class ArtistController {
    @Autowired
    ArtistService artistService;

    @GetMapping()
    public ResponseEntity<List<Artist>> getAllArtists(){
        return new ResponseEntity<>(artistService.allArtists(), HttpStatus.OK);
    }

    @GetMapping("/{artistId}")
    public ResponseEntity<ArtistResponse> getArtistById(@PathVariable String artistId){
        ArtistResponse response = artistService.artistById(artistId);

        if (response.getArtist() == null){
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/username/{name}")
    public ResponseEntity<Optional<List<Artist>>> getArtistsByName(@PathVariable String name){
        return new ResponseEntity<Optional<List<Artist>>>(artistService.artistsByName(name), HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArtistResponse> createArtist(@Valid @RequestBody ArtistRequest artistRequest){
        return new ResponseEntity<>(artistService.createArtist(artistRequest), HttpStatus.OK);
    }
}