import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../pages/home.css'

const API_DIR = "http://localhost:8080/";

function Album({id}) {
  const [oneAlbum, setoneAlbum] = useState([]);

  useEffect(() => {
    axios.get(API_DIR + 'api/albums/' + id)
      .then(response => {
        //console.log("API Response:", response.data);
        const oneAlbumData = response.data;
        console.log("DATA BEING PROCESSED:", oneAlbumData)
        setoneAlbum(oneAlbumData);
      })
      .catch(error => {
        console.error('Error fetching album:', error);
      });
    }, id);

const getColor = (score) =>{
    const scaling = (score/100) *120;
    return scaling
}

      return (
        <div className="album">
            {oneAlbum && (
                <>  
            <h1>Album: {oneAlbum.album.title}</h1>
            <content>
              <img src={oneAlbum.album.coverArt} alt={oneAlbum.album.title} class="left-aligned-image" />
              <h1>Artist: {oneAlbum.album.artist} <br /> 
              Release Date: {oneAlbum.album.releaseDate}<br />
              Score: {oneAlbum.album.score}<br /></h1>
            </content>
        </>
            )}
        </div>
    );
};

export default Album;