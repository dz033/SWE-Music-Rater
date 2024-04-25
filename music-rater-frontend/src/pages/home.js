import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ('./home.css');


function Home() {
  const [albums, setAlbums] = useState([]);
  
  useEffect(() => {
    // Fetch albums data from backend when component mounts
    axios.get('/api/albums')
      .then(response => {
        setAlbums(response.data);
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
          <div key={album.id} className="album-item">
            <img src={album.cover} alt={album.title} />
            <h3>{album.title}</h3>
            <p>{album.artist.name}</p> {/* Assuming you have artist name in your Album model */}
            <p>{album.releaseDate}</p>
          </div>
        ))}
      </div>
      </section>

    <section className="new-discoveries">
    <h1>New Discoveries</h1>
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