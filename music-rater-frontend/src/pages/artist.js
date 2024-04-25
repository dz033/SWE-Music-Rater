// ArtistPage.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';

function ArtistPage({ match }) {
  const [artist, setArtist] = useState(null);
  const artistId = match.params.id;

  useEffect(() => {
    // Define a function to fetch artist data
    const fetchArtist = async () => {
      try {
        // Send GET request to backend API to fetch artist data
        const response = await axios.get('/api/artist/${artistId}');
        // Update state with the received artist data
        setArtist(response.data);
      } catch (error) {
        console.error('Error fetching artist:', error);
      }
    };

    // Call fetchArtist function when component mounts
    fetchArtist();



  }, [artistId]);

  // Render loading message if artist data is being fetched
  if (!artist) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h1>{artist.name}</h1>
      <p>{artist.bio}</p>

      <h2>Albums</h2>
      <ul>
        {artist.albums.map(album => (
          <li key={album.id}>
            <strong>{album.title}</strong> - Released: {album.releaseDate}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ArtistPage;
//hi