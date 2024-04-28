import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ('./home.css');
import { Album } from '../components/Album';

const API_DIR = "http://localhost:8080/";
function Home() {
  const [albums, setAlbums] = useState([]);
  
  useEffect(() => {
    axios.get(API_DIR + 'api/albums')
      .then(response => {
        setAlbums(response.data.slice(0, 10));
      })
      .catch(error => {
        console.error('Error fetching albums:', error);
      });
  }, []);

  const featuredReviews = [
    { id: 1, title: "Worst album ever", author: "who?", content: "Here's what's up with this album." },
    { id: 2, title: "OMG im shocked", author: "ANON", content: "So good!" },
    // Add more reviews as needed
  ];

  return (
    <div className="home">
        <div className= "left-column">
        <section className="featured-albums">
      <h1>Featured Albums</h1>
      <div className="album-grid">
            {albums.map(album => (
              <div key={album.id}>
              <img src={album.coverArt} alt={album.title} />
              <h3>{album.title}</h3> 
              </div>
              ))}
          </div>
        </section>

    <section className="new-discoveries">
    <h1>New Discoveries</h1>
    <Album />
    </section>

    <section className="upcoming-releases">
    <h1>Upcoming Releases</h1>
    </section>
    </div>

    <div className="right-column">
    <section className="featured-reviews">
      <h2>Featured Reviews</h2>
      <div className="review-list">
        {featuredReviews.map(review => (
          <div key={review.id} className="review-item">
            <h3>{review.title}</h3>
            <p><strong>Author:</strong> {review.author}</p>
            <p>{review.content}</p>
          </div>
        ))}
      </div>
    </section>
  </div>
  </div>
  );
}





export default Home;