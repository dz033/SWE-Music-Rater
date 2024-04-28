package com.project.tempotalk.services;

import com.project.tempotalk.models.Album;
import com.project.tempotalk.payload.request.AlbumRequest;
import com.project.tempotalk.payload.response.MessageResponse;
import com.project.tempotalk.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    // Return a list of all albums in albumRepository
    public List<Album> allAlbums(){
        return albumRepository.findAll();
    }

    // Return a list of all albums that exist by a title, if there are any
    public Optional<List<Album>> albumsByTitle(String title){
        return albumRepository.findAlbumByTitle(title);
    }

    // Randomly select albums from the database
    public List<Album> getRandomAlbums(int numAlbums){
        List<Album> randomAlbums;

        SampleOperation sampleStage = Aggregation.sample(numAlbums);
        Aggregation aggregation = Aggregation.newAggregation(sampleStage);
        AggregationResults<Album> output = mongoTemplate.aggregate(aggregation, "albums", Album.class);
        randomAlbums = output.getMappedResults();

        return  randomAlbums;
    }

    // Create a new album object and store it in our "albums" collection
    public MessageResponse createAlbum(AlbumRequest albumRequest){
        Album album = new Album(albumRequest.getTitle(), albumRequest.getReleaseDate());
        albumRepository.save(album);
        return new MessageResponse("Album created successfully!");
    }
}
