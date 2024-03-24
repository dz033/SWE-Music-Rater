package com.musicrater.MusicRater.repositories;

import com.musicrater.MusicRater.models.ERole;
import com.musicrater.MusicRater.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
