import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './home.css';
import { Album } from '../components/Album';
import AlbumCarousel from './albumcarousel';

const API_DIR = "http://localhost:8080/";
function Home() {
  const [featuredAlbums, setFeaturedAlbums] = useState([]);
  const [newDiscoveries, setNewDiscoveries] = useState([]);
  const [newReleases, setnewReleases] = useState([]);

  useEffect(() => {
    axios.get(API_DIR + 'api/albums/discovery')
      .then(response => {
        const discoveries = response.data;
        setNewDiscoveries(discoveries);
      })
      .catch(error => {
        console.error('Error fetching albums:', error);
      });

    axios.get(API_DIR + 'api/albums/newReleases')
      .then(response => {
        const releases = response.data;
        setnewReleases(releases);
      })
      .catch(error => {
        console.error('Error fetching upcoming releases:', error);
      });
  }, []);

  return (
    /*<div className="home">
        <section className="featured-albums">
      <h1>Featured Albums</h1>
      <AlbumCarousel albums={albums} />
   </section>  */


   <div className="discoveries">
    <section className="discoveries">
          <h1>Discoveries</h1>
          <AlbumCarousel albums={newDiscoveries} />    
        </section>

        <section className="new-releases">
          <h1>New Releases</h1>
          <AlbumCarousel albums={newReleases} />
        </section>

      </div>
  );
}



export default Home;