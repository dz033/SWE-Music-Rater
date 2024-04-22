package com.project.tempotalk.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SongRequest {
    private String title;
    private String releaseDate;
}
