package com.project.tempotalk.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String id;
    private String name;
    private List<String> genres;
    @DocumentReference
    private List<Album> discography;
    @DocumentReference
    private List<Song> singles;
//    private Image image;
}
