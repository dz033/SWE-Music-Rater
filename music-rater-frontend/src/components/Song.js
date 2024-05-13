import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../pages/home.css'
import reviewService from '../services/reviewService';
import AuthService from '../services/authService';


const API_DIR = "http://localhost:8080/";

function Song({id}) {
  const [song, setSong] = useState([]);
  const [reviewRating, setReviewRating] = useState('');
  const [reviewBody, setReviewBody] = useState('');
  const userId = AuthService.getCurrentUser()?.id;
  useEffect(() => {
    const fetchSongData = async () => {
        try {
          const response = await axios.get(API_DIR + 'api/songs/' + id);
          const songData = response.data.song;
          setSong(songData);
        } catch (error) {
          console.error('Error fetching song:', error);
        }
      };
  
      fetchSongData();
    }, [id]);
    const handleReviewSubmit = async (e) => {
        e.preventDefault();
        try {
          await reviewService.createReview(reviewBody, reviewRating, userId, oneAlbum.id);
          // Clear review inputs after submission
          setReviewRating('');
          setReviewBody('');
          alert('Review submitted successfully!');
        } catch (error) {
          console.error('Error submitting review:', error);
          alert('Failed to submit review. Please try again.');
        }
      };
    
      const handleKeyDown = (e) => {
        if (e.key !== 'Enter') {
          return;
        }
        handleReviewSubmit(e);
      };
    

    return (
        <div className="song">
        {song && (
          <>
            <h1>Album: {song.title}</h1>
            <content>
              <img src={song.coverArt} alt={song.title} className="left-aligned-image" />
              <h1>
                Artist: {song.artist} <br />
                Release Date: {song.releaseDate}<br />
                Score: {song.score}<br />
              </h1>
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
        // <div className="song">
        //     <h1>Song: {song.title} </h1>
        //     <img src={song.coverArt} className="song-image" />
        //     <h1>
        //       Artist: {song.artist} <br />
        //       Release Date: {song.releaseDate}<br />
        //       Score: {song.score}<br />
        //     </h1>
        // </div>
      );
};

export default Song;