import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './home.css';
import { Album } from '../components/Album';
import AlbumCarousel from './albumcarousel';

const API_DIR = "http://localhost:8080/";
function Home() {
  const [albums, setAlbums] = useState([]);

  useEffect(() => {
    axios.get(API_DIR + 'api/albums')
      .then(response => {
        setAlbums(response.data.slice(0, 21));
      })
      .catch(error => {
        console.error('Error fetching albums:', error);
      });
  }, []);

  return (
    <div className="home">
        <section className="featured-albums">
      <h1>Featured Albums</h1>
      <AlbumCarousel albums={albums} />
   </section> 


   <section className="new-discoveries">
          <h1>New Discoveries</h1>
          <Album />
        </section>

        <section className="upcoming-releases">
          <h1>Upcoming Releases</h1>
        </section>

      </div>
  );
}



export default Home;