package com.musicrater.MusicRater.models;

import com.musicrater.MusicRater.models.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private ObjectId id;
    private String username;
    private List<Integer> followers;
    private List<Integer> following;
    private List<Integer> reviews;
    private String email;
    private String dateOfBirth;
    private String password;
    private String creationDate;
    private Image profileImage;
}
