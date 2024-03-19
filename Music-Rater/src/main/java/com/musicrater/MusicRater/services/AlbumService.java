package com.musicrater.MusicRater.services;

import com.musicrater.MusicRater.repositories.AlbumRepository;
import com.musicrater.MusicRater.models.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;
    public List<Album> allAlbums(){
        return albumRepository.findAll();
    }

    public Optional<List<Album>> albumsByTitle(String title){
        return albumRepository.findAlbumByTitle(title);
    }
}
