package com.project.tempotalk.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

@Document(collection="albums")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album {
    @Id
    private String id;
    private String title;
    private String artist = "";
    private String releaseDate;
    private int score = 0;
    private List<String> tracklist = new ArrayList<>();
    private List<String> genres = new ArrayList<>();
    private List<String> reviews = new ArrayList<>();
    private String coverArt = "https://tempotalk-images.s3.ap-northeast-2.amazonaws.com/coverArt/defaultCoverArt.png";

    public Album(String title, String releaseDate){
        this.title = title;
        this.releaseDate = releaseDate;
    }

    // Calculate the average score for this album
    public void calculateScore(List<Integer> scores){
        int numRatings = 0;
        int total = 0;
        for (int score : scores){
            total += score;
            numRatings++;
        }
        setScore(total / numRatings);
    }
}