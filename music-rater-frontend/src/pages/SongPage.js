import React from 'react';
import { useParams } from 'react-router-dom';
import Song from '../components/Song'
import Review from '../components/Review'
import './home.css'
function SongPage() {
    const { id } = useParams(); // Get the id parameter from the URL
    //console.log("ID from URL:", id); 



return (
    <div className = "song-page">
      <Song id={id} /> {/* Pass the id to the Album component */}

      <Review id = {id} />
    </div>
  );
}

export default SongPage;