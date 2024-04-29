import React from 'react';
import { useParams } from 'react-router-dom';
import Album from '../components/Album';

function AlbumPage() {
    const { id } = useParams(); // Get the id parameter from the URL
    console.log("ID from URL:", id); 
return (
    <div>
      <h2>Album Page</h2>
      <Album id={id} /> {/* Pass the id to the Album component */}
    </div>
  );
}

export default AlbumPage;