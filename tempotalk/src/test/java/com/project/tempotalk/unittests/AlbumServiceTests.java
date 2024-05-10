package com.project.tempotalk.unittests;

import com.project.tempotalk.models.Album;
import com.project.tempotalk.payload.request.AlbumRequest;
import com.project.tempotalk.payload.response.AlbumResponse;
import com.project.tempotalk.repositories.AlbumRepository;
import com.project.tempotalk.services.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTests {

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private AlbumService albumService;

    private Album album;
    private List<Album> albums;

    @BeforeEach
    public void init(){
        album = new Album("title","artist","releaseDate",new ArrayList<String>());
        albums = new ArrayList<>();
        albums.add(album);
    }

    @Test
    public void UserService_AlbumsById_NotFound(){
        when(albumRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        AlbumResponse response = albumService.albumById("id");
        assertThat(response.getMessage()).isEqualTo("Error: album was not found");
        assertThat(response.getAlbum()).isNull();
    }

    @Test
    public void UserService_AlbumsById_SuccessfullyFound(){
        when(albumRepository.findById(Mockito.anyString())).thenReturn(Optional.of(album));
        AlbumResponse response = albumService.albumById("id");
        assertThat(response.getMessage()).isEqualTo("Album was found successfully");
        assertThat(response.getAlbum()).isNotNull();
    }

    @Test
    public void UserService_AlbumsByTitle_NotFound(){
        when(albumRepository.findAlbumByTitle(Mockito.anyString())).thenReturn(Optional.empty());
        List<Album> response = albumService.albumsByTitle("title");
        assertThat(response).isEmpty();
    }

    @Test
    public void UserService_AlbumsByTitle_SuccessfullyFound(){
        when(albumRepository.findAlbumByTitle(Mockito.anyString())).thenReturn(Optional.of(albums));
        List<Album> response = albumService.albumsByTitle("title");
        assertThat(response).isNotEmpty();
    }

    @Test
    public void UserService_CreateAlbum_Success(){
        AlbumRequest request = new AlbumRequest("title","artist","releaseDate", new ArrayList<>());
        when(albumRepository.save(Mockito.any(Album.class))).thenReturn(album);
        AlbumResponse response = albumService.createAlbum(request);
        assertThat(response.getMessage()).isEqualTo("Album created successfully!");
        assertThat(response.getAlbum()).isNotNull();
    }
}