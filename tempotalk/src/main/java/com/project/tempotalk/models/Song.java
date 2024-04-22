package com.project.tempotalk.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection="songs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    @Id
    private String id;
    private String title;
    @DocumentReference
    private Artist artist;
    private String releaseDate;
    private int score;
    private List<String> genres;
    private List<String> reviews;
}
