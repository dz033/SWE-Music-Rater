package com.musicrater.MusicRater.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection="images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    private ObjectId id;
    private Date creationDate;
    //private File file;
}
