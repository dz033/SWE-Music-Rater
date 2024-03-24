package com.musicrater.MusicRater.models;

import com.musicrater.MusicRater.models.Image;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    @NotBlank
    @Size(max = 25)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    @Size(max = 120)
    private String password;
    private String creationDate;
    private List<Integer> followers;
    private List<Integer> following;
    private List<Integer> reviews;
    @DBRef
    private Set<Role> roles = new HashSet<>();
    private Image profileImage;

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
