package com.project.tempotalk.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditPlaylistRequest {
    private String songId;
    private String playlistId;
}