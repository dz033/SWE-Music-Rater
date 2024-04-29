import React, { useState, useEffect } from 'react';
import axios from 'axios';

const API_DIR = "http://localhost:8080/";

function Album({id}) {
  const [oneAlbum, setoneAlbum] = useState(null);

  useEffect(() => {
    axios.get(API_DIR + 'api/albums/${title}')
      .then(response => {
        console.log("API Response:", response.data);
        const oneAlbumData = response.data;
        setoneAlbum(oneAlbumData);
      })
      .catch(error => {
        console.error('Error fetching album:', error);
      });
    }, [id]);

    console.log("oneAlbum state:", oneAlbum);

      return (
        <div className="album">
            {oneAlbum && (
                <>
            <img src={oneAlbum.cover} alt={oneAlbum.title} />
            <p>{oneAlbum.title}</p>
            <p>{oneAlbum.artist}</p>
             <p>Release Date: {oneAlbum.releaseDate}</p>
            <p>Score: {oneAlbum.score}/5</p>
        </>
            )}
        </div>
    );
};

export default Album;