import React, { useState, useEffect, useRef} from 'react';
import Carousel from 'react-bootstrap/Carousel';
import { Link } from 'react-router-dom';

import './home.css'
function AlbumCarousel({ albums }) {
  const [index, setIndex] = useState(0);
  const carouselRef = useRef(null);
  const [albumWidth, setAlbumWidth] = useState(0);

  useEffect(() => {
    if (carouselRef.current) {
      const carouselWidth = carouselRef.current.offsetWidth;
      const albumsPerSlide = 3; // Change this value as needed
      const calculatedWidth = carouselWidth / albumsPerSlide;
      setAlbumWidth(calculatedWidth);
    }
  }, [albums]);

  const handleSelect = (selectedIndex) => {
    setIndex(selectedIndex);
  };

  const albumsPerSlide = 3;
  const groupedAlbums = [];
  for (let i = 0; i < albums.length; i += albumsPerSlide) {
    groupedAlbums.push(albums.slice(i, i + albumsPerSlide));
  }

  return (
    <div className="album-carousel" ref={carouselRef}>
      <Carousel activeIndex={index} onSelect={handleSelect}>
        {groupedAlbums.map((albumGroup, idx) => (
          <Carousel.Item key={idx}>
            <div className="album-group">
              {albumGroup.map((album, albumIdx) => (
                <div key={albumIdx} className="album-item" style={{ width: `${albumWidth}px`}}>
                  <Link to={`/album/${album.id}`}>
                  <div className="album-cover">
                    <img src={album.coverArt} alt={album.title} />
                    </div>
                    <h5 className="album-title">{album.title}</h5>
                  </Link>
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