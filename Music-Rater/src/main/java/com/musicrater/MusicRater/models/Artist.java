package com.musicrater.MusicRater.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection="artists")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    @Id
    private ObjectId id;
    private String name;
    private int score;
    private int likes;
    private List<String> genres;
    @DocumentReference
    private List<Album> discography;
    @DocumentReference
    private List<Song> singles;
}
