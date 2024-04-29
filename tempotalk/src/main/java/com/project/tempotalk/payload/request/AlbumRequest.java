package com.project.tempotalk.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AlbumRequest {
    private String title;
    private String artist;
    private String releaseDate;
    private List<String> genres;
}
