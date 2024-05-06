package com.project.tempotalk.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ArtistRequest {
    private String name;
    private List<String> genres;
}