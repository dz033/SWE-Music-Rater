package com.project.tempotalk.repositories;

import com.project.tempotalk.models.Song;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends MongoRepository<Song, ObjectId> {
}
