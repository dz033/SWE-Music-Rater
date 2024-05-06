package com.project.tempotalk.services;

import com.project.tempotalk.models.Artist;
import com.project.tempotalk.models.Song;
import com.project.tempotalk.payload.request.ArtistRequest;
import com.project.tempotalk.payload.response.ArtistResponse;
import com.project.tempotalk.payload.response.SongResponse;
import com.project.tempotalk.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {
    @Autowired
    ArtistRepository artistRepository;

    public List<Artist> allArtists(){
        return artistRepository.findAll();
    }

    // Retrieve and return a song based on its ID
    public ArtistResponse artistById(String artistId){
        Optional<Artist> tempArtist = artistRepository.findById(artistId);
        if (tempArtist.isEmpty()){
            return new ArtistResponse("Error: Artist was not found");
        }
        return new ArtistResponse(tempArtist.get(), "Artist was found successfully");
    }

    public Optional<List<Artist>> artistsByName(String name){
        return artistRepository.findArtistByName(name);
    }

    // Create a new Artist object and store it in our "artists" collection
    public ArtistResponse createArtist(ArtistRequest artistRequest){
        Artist artist = new Artist(artistRequest.getName(), artistRequest.getGenres());
        artistRepository.save(artist);
        return new ArtistResponse(artist,"Artist created successfully!");
    }
}