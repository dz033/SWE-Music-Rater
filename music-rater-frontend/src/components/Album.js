import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../pages/home.css';
import reviewService from '../services/reviewService';
import AuthService from '../services/authService';
import songService from '../services/songService';

const API_DIR = "http://localhost:8080/";

function Album({ id }) {
  const [oneAlbum, setOneAlbum] = useState(null);
  const [reviewRating, setReviewRating] = useState('');
  const [reviewBody, setReviewBody] = useState('');
  const [songs, setSongs] = useState([]);

  const userId = AuthService.getCurrentUser()?.id;

  useEffect(() => {
    const fetchAlbumData = async () => {
      try {
        const response = await axios.get(API_DIR + 'api/albums/' + id);
        const oneAlbumData = response.data.album;
        const songsData = await Promise.all(oneAlbumData.tracklist.map(async (trackId) => {
          try {
            const songData = await songService.getSongByID(trackId);
            return songData; // Return the fetched song data
          } catch (error) {
            console.error('Error fetching song:', error);
            return null; // Return null if there's an error fetching the song
          }
        }));

        // Filter out any null values (indicating errors) from the songsData array
        const validSongsData = songsData.filter(songData => songData !== null);

        // Update the songs state with the valid song data
        setSongs(validSongsData);
        setOneAlbum(oneAlbumData);
      } catch (error) {
        console.error('Error fetching album:', error);
      }
    };

    fetchAlbumData();
  }, [id]);

  const handleReviewSubmit = async (e) => {
    e.preventDefault();
    try {
      await reviewService.createReview(reviewBody, reviewRating, userId, oneAlbum.id);
      // Clear review inputs after submission
      setReviewRating('');
      setReviewBody('');
      //alert('Review submitted successfully!');
    } catch (error) {
      console.error('Error submitting review:', error);
      //alert('Failed to submit review. Please try again.');
    }
  };

  const handleKeyDown = (e) => {
    if (e.key !== 'Enter') {
      return;
    }
    handleReviewSubmit(e);
  };

  return (
    <div className="album">
      {oneAlbum && (
        <>
          <h1>Album: {oneAlbum.title}</h1>
          <content>
            <img src={oneAlbum.coverArt} alt={oneAlbum.title} className="left-aligned-image" />
            <h1>
              Artist: {oneAlbum.artist} <br />
              Release Date: {oneAlbum.releaseDate}<br />
              Score: {oneAlbum.score}<br />
              Songs: 
            </h1>
            {/* Displaying songs */}
            <ul>
              {Array.isArray(songs) && songs.map((song) => (
                <li key={song.id}>
                  <a href={`http://localhost:3000/song/${song.id}`}>
                    {song.title}
                  </a>
                  , {song.score}
                </li>
              ))}
            </ul>
            <form onSubmit={handleReviewSubmit} onKeyDown={handleKeyDown}>
              <input
                placeholder="Enter a score from 0 to 100"
                type="text"
                value={reviewRating}
                onChange={(e) => setReviewRating(e.target.value)}
              />
              <input
                placeholder="Write your thoughts"
                type="text"
                value={reviewBody}
                onChange={(e) => setReviewBody(e.target.value)}
              />
              <button type="submit">Create Review</button>
            </form>
          </content>
        </>
      )}
    </div>
  );
}

export default Album;
