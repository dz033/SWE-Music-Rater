import React from 'react';
import { useParams } from 'react-router-dom';
import Album from '../components/Album';
import Review from '../components/Review'
import './home.css'
function AlbumPage() {
    const { id } = useParams(); // Get the id parameter from the URL
    //console.log("ID from URL:", id); 



return (
    <div className = "album-page">
      <h2></h2>
      <Album id={id} /> {/* Pass the id to the Album component */}

      <Review id = {id} />

    </div>
  );
}

export default AlbumPage;