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
        const albums = response.data.slice(0, 63); // Fetching 63 albums
      })
      .catch(error => {
        console.error('Error fetching albums:', error);
      });
  }, []);

  const featuredAlbums = albums.slice(0, 21); // First 21 albums for featured
  const newDiscoveries = albums.slice(21, 42); // Next 21 albums for new discoveries
  const upcomingReleases = albums.slice(42, 63);
  
  return (
    <div className="home">
        <section className="featured-albums">
      <h1>Featured Albums</h1>
      <AlbumCarousel albums={albums} />
   </section> 


   <section className="new-discoveries">
          <h1>New Discoveries</h1>
          <AlbumCarousel albums={newDiscoveries} />    
        </section>

        <section className="upcoming-releases">
          <h1>Upcoming Releases</h1>
          <AlbumCarousel albums={upcomingReleases} />
        </section>

      </div>
  );
}



export default Home;