package com.project.tempotalk.services;

import com.project.tempotalk.models.Album;
import com.project.tempotalk.payload.request.AlbumRequest;
import com.project.tempotalk.payload.response.MessageResponse;
import com.project.tempotalk.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

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

    // Retrieve and return an album based on its ID
    public Optional<Album> albumById(String albumId){ return albumRepository.findById(albumId); }

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

    // Get the most newly released albums in the database
    public List<Album> getNewAlbums(int numAlbums){
        List<Album> newAlbums;

        Query query = new Query();
        query.limit(numAlbums);
        query.with(Sort.by(new Sort.Order(Sort.Direction.DESC, "releaseDate")));
        newAlbums = mongoTemplate.find(query, Album.class);

        return  newAlbums;
    }

    // Create a new album object and store it in our "albums" collection
    public MessageResponse createAlbum(AlbumRequest albumRequest){
        Album album = new Album(albumRequest.getTitle(), albumRequest.getArtist(), albumRequest.getReleaseDate(), albumRequest.getGenres());
        albumRepository.save(album);
        return new MessageResponse("Album created successfully!");
    }
}
