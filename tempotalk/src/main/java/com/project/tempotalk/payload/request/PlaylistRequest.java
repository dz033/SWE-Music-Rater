package com.project.tempotalk.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaylistRequest {
    private String name;
    private String description;
    private String creatorId;

    public PlaylistRequest(String name, String creatorId){
        this.name = name;
        this.creatorId = creatorId;
    }
}
