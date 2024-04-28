import React, { useState } from 'react';
import Carousel from 'react-bootstrap/Carousel';
import './home.css'

function AlbumCarousel({ albums }) {
  const [index, setIndex] = useState(0);

  const handleSelect = (selectedIndex) => {
    setIndex(selectedIndex);
  };

  return (
    <div className="album-carousel">
    <Carousel activeIndex={index} onSelect={handleSelect}>
      {albums.map((album, idx) => (
        <Carousel.Item key={idx}>
          <img
            src={album.coverArt}
            alt={album.title}
          />
          <Carousel.Caption>
            <h3>{album.title}</h3>
            {/* Add more details here if needed */}
          </Carousel.Caption>
        </Carousel.Item>
      ))}
    </Carousel>
    </div>

  );
}

export default AlbumCarousel;