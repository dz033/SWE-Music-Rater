import React, { useState, useEffect } from 'react';
import axios from 'axios';
import "./song.css";

function Song({ songId }) {
  const [song, setSong] = useState(null);
  const [reviews, setReviews] = useState([]);

  // Fetch song data 
  useEffect(() => {
    const fetchSong = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/songs/662651c863590b099f301503');
        setSong(response.data);
      } catch (error) {
        console.error('Error fetching song:', error);
      }
    };

    fetchSong();
  }, [songId]);

  // Fetch review for the song
  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/songs/${songId}/reviews');
        setReviews(response.data);
      } catch (error) {
        console.error('Error fetching reviews:', error);
      }
    };

    fetchReviews();
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

      <div className="review-section">
        <h2>Review</h2>
        <ul>
          {reviews.map((review) => (
            <li key={review.id}>{review.text}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default Song;
