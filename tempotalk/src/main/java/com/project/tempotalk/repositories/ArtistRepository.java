package com.project.tempotalk.repositories;

import com.project.tempotalk.models.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends MongoRepository<Artist, String> {
    Optional<List<Artist>> findArtistByName(String name);
}
