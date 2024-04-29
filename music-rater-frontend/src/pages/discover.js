/*import "./discover.css"



export default function Discover(){
    return(
        <>
        <div className="discover-page"></div>
            <h1>Discover</h1>
            <p>Randomize albums or carousel per genre type?</p> 
                
        </>
    )
}*/
import React, { useState, useEffect } from 'react';
import AlbumCarousel from './albumcarousel';

export default function Discover() {
   
    const [albums, setAlbums] = useState([]);

    useEffect(() => {
        
        fetchAlbumsData();
    }, []);

    const fetchAlbumsData = async () => {
        try {
            
            const response = await fetch('http://localhost:8080/api/albums');
            const data = await response.json();
            
            setAlbums(data);
        } catch (error) {
            console.error('Error fetching albums data:', error);
        }
    };

    return (
        <>
            <div className="discover-page"></div>
            <h1>Discover</h1>
            <h2 style={{ fontFamily: 'Arial, sans-serif', color: 'white' }}>What is your preferred record?</h2>

           
            <AlbumCarousel albums={albums} />
        </>
    );
}
