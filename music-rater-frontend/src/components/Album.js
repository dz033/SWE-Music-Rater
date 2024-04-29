import React, { useState, useEffect } from 'react';
import axios from 'axios';

const API_DIR = "http://localhost:8080/";

function Album({id}) {
  const [oneAlbum, setoneAlbum] = useState([]);

  useEffect(() => {
    axios.get(API_DIR + 'api/albums/' + id)
      .then(response => {
        console.log("API Response:", response.data);
        const oneAlbumData = response.data;
        setoneAlbum(oneAlbumData);
      })
      .catch(error => {
        console.error('Error fetching album:', error);
      });
    }, id);



      return (
        <div className="album">
            {oneAlbum && (
                <>
            <h1>{oneAlbum.title}</h1>
            <content>
              <img src={oneAlbum.coverArt} alt={oneAlbum.title} />
              <p>Artist: {oneAlbum.artist} <br></br></p>
              <p>Release Date: {oneAlbum.releaseDate}</p>
              <p>Score: {oneAlbum.score}/100</p>
            </content>
        </>
            )}
        </div>
    );
};

export default Album;