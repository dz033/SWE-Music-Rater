package com.project.tempotalk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

@Document(collection="artists")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    @Id
    private String id;
    private String name;
    private List<String> genres = new ArrayList<>();
    private List<String> discography = new ArrayList<>();
    private List<String> singles = new ArrayList<>();
//  private Image image;

    public Artist(String name){
        this.name = name;
    }
}
