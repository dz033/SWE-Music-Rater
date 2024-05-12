import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../pages/home.css'

const API_DIR = "http://localhost:8080/";

function Review({id}) {
  const [reviews, setReviews] = useState([]);

  useEffect(() => {
    axios.get(API_DIR + 'api/reviews/' + id)
      .then(response => {
        console.log("API Response:", response.data);
        const reviewsData = response.data;
        //console.log("DATA BEING PROCESSED:", reviewsData)
        setReviews(reviewsData);
      })
      .catch(error => {
        console.error('Error fetching album:', error);
      });
    }, id);
    

    return (
        <div className="review">
          <h1>Reviews:</h1>
          <div className="review-list">
            {reviews.map(r => (
              <div key={r.review.id} className="review-item">
                <p>Score: {r.review.score}</p>
                <p>{r.review.body}</p>
                <p>Date: {r.review.creationDate.slice(0, 10)}</p>

              </div>
            ))}
          </div>
        </div>
      );
};

export default Review;