package com.project.tempotalk.payload.response;

import com.project.tempotalk.models.Artist;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArtistResponse {
    private Artist artist = null;
    private String message;

    public ArtistResponse(String message){
        this.message = message;
    }
}