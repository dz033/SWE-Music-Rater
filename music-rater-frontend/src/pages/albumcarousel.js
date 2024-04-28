import React, { useState } from 'react';
import Carousel from 'react-bootstrap/Carousel';
import './home.css'

function AlbumCarousel({ albums }) {
  const [index, setIndex] = useState(0);

  const handleSelect = (selectedIndex) => {
    setIndex(selectedIndex);
  };

  const albumsPerSlide = 3;
  const groupedAlbums = [];
  for (let i = 0; i < albums.length; i += albumsPerSlide) {
    groupedAlbums.push(albums.slice(i, i + albumsPerSlide));
  }

  return (
    <div className="album-carousel">
    <Carousel activeIndex={index} onSelect={handleSelect}>
    {groupedAlbums.map((albumGroup, idx) => (
        <Carousel.Item key={idx}>
            <div className="album-group">
            {albumGroup.map((album, albumIdx) => (
                <div key={albumIdx} className="album-item">
                  <img src={album.coverArt} alt={album.title} />
                  <h5>{album.title}</h5>
                </div>
                ))}
                </div>
        </Carousel.Item>
    ))}
    </Carousel>
    </div>

  );
}

export default AlbumCarousel;