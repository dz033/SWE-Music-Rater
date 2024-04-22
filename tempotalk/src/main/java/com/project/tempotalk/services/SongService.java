package com.project.tempotalk.services;

import com.project.tempotalk.models.Song;
import com.project.tempotalk.payload.request.SongRequest;
import com.project.tempotalk.payload.response.MessageResponse;
import com.project.tempotalk.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongService {
    @Autowired
    SongRepository songRepository;

    // Create a new Song object and store it in our "songs" collection
    public MessageResponse createSong(SongRequest songRequest){
        Song song = new Song(songRequest.getTitle(), songRequest.getReleaseDate());
        songRepository.save(song);
        return new MessageResponse("Song created successfully!");
    }
}
