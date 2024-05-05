package com.project.tempotalk.payload.response;

import com.project.tempotalk.models.Album;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlbumResponse {
    private Album album = null;
    private String message;

    public AlbumResponse(String message){
        this.message = message;
    }
}