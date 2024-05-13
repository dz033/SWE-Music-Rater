import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../pages/home.css';

const API_DIR = "http://localhost:8080/api/playlists/user/";

function Playlist({ id }) {
    const [playlists, setPlaylists] = useState([]);

    useEffect(() => {
        axios.get(API_DIR + id)
            .then(response => {
                console.log("playlist API Response:", response.data);
                setPlaylists(response.data);
            })
            .catch(error => {
                console.error('Error fetching playlists:', error);
            });
    }, [id]);

    const [songs, setSongs] = useState({});

    useEffect(() => {
        const fetchData = async () => {
            try {
                const songsData = {};
                await Promise.all(playlists.map(async (playlist) => {
                    const playlistSongs = await Promise.all(playlist.tracks.map(async (track) => {
                        const response = await axios.get('http://localhost:8080/api/songs/' + track);
                        //console.log('SONG ERSPONSE', response.data.song)
                        return response.data.song.title;
                    }));
                    songsData[playlist.id] = playlistSongs;
                }));
                setSongs(songsData);
            } catch (error) {
                console.error('Error fetching songs:', error);
            }
        };
        fetchData();
    }, [playlists]);

    return (
        <div className="playlists">
            <h1>Playlists:</h1>
            {playlists.length > 0 ? (
                playlists.map((playlist, index) => (
                    <div key={index}>
                        <h2>{playlist.name}</h2>
                        <ul>
                            {songs[playlist.id] && songs[playlist.id].map((song, songIndex) => (
                                <li key={songIndex}>{song}</li>
                            ))}
                        </ul>
                    </div>
                ))
            ) : (
                <p>No playlists found</p>
            )}
        </div>
    );
}

export default Playlist;
