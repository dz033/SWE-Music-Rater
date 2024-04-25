import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './song.css';

function SongPage({ songId }) {
  const [song, setSong] = useState(null);
  const [comments, setComments] = useState([]);

  // Fetch song data 
  useEffect(() => {
    const fetchSong = async () => {
      try {
        const response = await axios.get(`/api/songs/${songId}`);
        setSong(response.data);
      } catch (error) {
        console.error('Error fetching song:', error);
      }
    };

    fetchSong();
  }, [songId]);

  // Fetch comments for the song
  useEffect(() => {
    const fetchComments = async () => {
      try {
        const response = await axios.get(`/api/songs/${songId}/comments`);
        setComments(response.data);
      } catch (error) {
        console.error('Error fetching comments:', error);
      }
    };

    fetchComments();
  }, [songId]);

  return (
    <div className="song-page">
      {song && (
        <div className="song-details">
          <h1>{song.title}</h1>
          <p>Artist: {song.artist}</p>
          <p>Album: {song.album}</p>
          <p>Release Date: {song.releaseDate}</p>
          <p>Duration: {song.duration}</p>
        </div>
      )}

      <div className="comments-section">
        <h2>Comments</h2>
        <ul>
          {comments.map((comment) => (
            <li key={comment.id}>{comment.text}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default SongPage;
