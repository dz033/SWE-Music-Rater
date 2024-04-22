package com.project.tempotalk.services;

import com.project.tempotalk.models.Artist;
import com.project.tempotalk.payload.request.ArtistRequest;
import com.project.tempotalk.payload.response.MessageResponse;
import com.project.tempotalk.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {
    @Autowired
    ArtistRepository artistRepository;

    // Create a new Artist object and store it in our "artists" collection
    public MessageResponse createArtist(ArtistRequest artistRequest){
        Artist artist = new Artist(artistRequest.getName());
        artistRepository.save(artist);
        return new MessageResponse("Artist created successfully!");
    }
}
