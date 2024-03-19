package com.musicrater.MusicRater.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection="albums")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album {
    @Id
    private ObjectId id;
    private String title;
    @DocumentReference
    private Artist artist;
    private String releaseDate;
    private int score;
    private int likes;
    private List<String> tracklist;
    private List<String> genres;
    @DocumentReference
    private List<Review> reviews;
}