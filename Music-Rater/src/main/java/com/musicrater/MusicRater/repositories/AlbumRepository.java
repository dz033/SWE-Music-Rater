package com.musicrater.MusicRater.repositories;

import com.musicrater.MusicRater.models.Album;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends MongoRepository<Album, ObjectId> {
    Optional<List<Album>> findAlbumByTitle(String title);
}