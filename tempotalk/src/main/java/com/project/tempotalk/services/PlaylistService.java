package com.project.tempotalk.services;

import com.project.tempotalk.models.Playlist;
import com.project.tempotalk.models.User;
import com.project.tempotalk.payload.request.PlaylistRequest;
import com.project.tempotalk.payload.response.PlaylistResponse;
import com.project.tempotalk.repositories.PlaylistRepository;
import com.project.tempotalk.repositories.SongRepository;
import com.project.tempotalk.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    @Autowired
    PlaylistRepository playlistRepository;

    @Autowired
    SongRepository songRepository;

    @Autowired
    UserRepository userRepository;

    // Create a Playlist and assign it to the user who created it
    public PlaylistResponse createPlaylist(PlaylistRequest playlistRequest){
        // Find User who is creating the new playlist
        Optional<User> tempUser = userRepository.findById(playlistRequest.getCreatorId());
        if (tempUser.isEmpty()){
            return new PlaylistResponse("User could not be found");
        }
        User user = tempUser.get();

        // Create Playlist and save it in the repository
        Playlist playlist;
        if (playlistRequest.getDescription() == null){
            playlist = new Playlist(playlistRequest.getName(), playlistRequest.getCreatorId());
        }
        else{
            playlist = new Playlist(playlistRequest.getName(), playlistRequest.getDescription(), playlistRequest.getCreatorId());
        }
        playlistRepository.save(playlist);

        // Update User's playlists
        List<String> userPlaylists = user.getPlaylists();
        userPlaylists.add(playlist.getId());
        user.setPlaylists(userPlaylists);
        userRepository.save(user);

        return new PlaylistResponse(playlist, "Playlist created successfully!");
    }
}