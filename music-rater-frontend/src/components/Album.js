import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../pages/home.css'
import reviewService from '../services/reviewService';
import AuthService from '../services/authService';

const API_DIR = "http://localhost:8080/";

function Album({ id }) {
  const [oneAlbum, setOneAlbum] = useState([]);
  const [reviewRating, setReviewRating] = useState('');
  const [reviewBody, setReviewBody] = useState('');
  const userId = AuthService.getCurrentUser()?.id;


  useEffect(() => {
    const fetchAlbumData = async () => {
      try {
        const response = await axios.get(API_DIR + 'api/albums/' + id);
        const oneAlbumData = response.data.album;
        setOneAlbum(oneAlbumData);
      } catch (error) {
        console.error('Error fetching album:', error);
      }
    };

    fetchAlbumData();
  }, [id]); // Make useEffect dependent on id

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
  );
}

export default Album;