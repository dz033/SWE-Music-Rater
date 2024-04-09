package com.project.tempotalk.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

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
    @CreatedDate
    private Date creationDate;
    @DBRef
    private List<User> following = new ArrayList<>();
    @DBRef
    private List<Review> reviews = new ArrayList<>();
    @DBRef
    private Set<Role> roles = new HashSet<>();
//    private java.awt.Image profileImage = null;

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
        this.creationDate = new Date();
        this.following = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }
}