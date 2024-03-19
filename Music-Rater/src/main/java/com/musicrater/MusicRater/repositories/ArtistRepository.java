package com.musicrater.MusicRater.repositories;

import com.musicrater.MusicRater.models.Artist;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends MongoRepository<Artist, ObjectId> {
}
