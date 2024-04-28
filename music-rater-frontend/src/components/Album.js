import React, { useState, useEffect } from 'react';

export const Album = () => {
    const [albumData, setAlbumData] = useState(null);
    useEffect(() => {
        fetchAlbumData(); 
      }, []);
      const fetchAlbumData = async () => {
        try {
          const response = await fetch('http://localhost:8080/api/album');
          const data = await response.json();
          setAlbumData(data); 
        } catch (error) {
          console.error('Error fetching album data:', error);
        }
      };
      
    
    

      return (
        <div className="album">
            {albumData && (
                <>
                    <img src={albumData.cover} alt={albumData.title} />
                    <p>{albumData.title}</p>
                    <p>{albumData.artist}</p>
                    <p>Release Date: {albumData.releaseDate}</p>
                    <p>Score: {albumData.score}/5</p>
                </>
            )}
        </div>
    );
    
};


