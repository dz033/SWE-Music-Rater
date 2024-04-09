package com.project.tempotalk.services;

import com.project.tempotalk.models.Album;
import com.project.tempotalk.repositories.AlbumRepository;
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
