package com.project.tempotalk.payload.response;

import com.project.tempotalk.models.Song;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SongResponse {
    private Song song = null;
    private String message;

    public SongResponse(String message){
        this.message = message;
    }
}