import React, { useState, useEffect } from 'react';
import axios from 'axios';

const API_DIR = "http://localhost:8080/";

function Review({music_id}) {
  const [reviews, setReview] = useState([]);

  useEffect(() => {
    axios.get(API_DIR + 'api/reviews/' + music_id)
      .then(response => {
        console.log("review Response:", response.data);
        const reviewsData = response.data;
        setReview(reviewsData);
      })
      .catch(error => {
        console.error('Error fetching album:', error);
      });
    }, music_id);
    

    return (
        <div className="review">
          <h1>Reviews</h1>
          <div className="review-list">
            {reviews.map(review => (
              <div key={review.id} className="review-item">
                <p>Score: {review.score}</p>
                <p>{review.body}</p>
                <p>Date: {review.creationDate.slice(0, 10)}</p>
                
              </div>
            ))}
          </div>
        </div>
      );
};

export default Review;