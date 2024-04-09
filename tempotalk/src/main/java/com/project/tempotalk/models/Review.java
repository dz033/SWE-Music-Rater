package com.project.tempotalk.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;

@Document(collection="reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    private String id;
    @DocumentReference
    private User creator;
    private int score;
    private String body = "";
    private Date creationDate;

    public Review(int score){
        this.score = score;
    }
    public Review(String body){
        this.body = body;
    }

    public Review(String body, int score){
        this.body = body;
        this.score = score;
        this.creator = null;
        this.creationDate = null;
    }
}
