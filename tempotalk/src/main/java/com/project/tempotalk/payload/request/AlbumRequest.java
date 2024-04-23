package com.project.tempotalk.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlbumRequest {
    private String title;
    private String releaseDate;
}
