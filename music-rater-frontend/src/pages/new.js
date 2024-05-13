import './new.css';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const API_DIR = "http://localhost:8080/";

export default function NewPage() {
    const [newReleases, setNewReleases] = useState([]);

    useEffect(() => {
        axios.get(API_DIR + 'api/albums/newReleases')
        .then(response => {
            const releases = response.data;
            setNewReleases(releases);
        })
        .catch(error => {
            console.error('Error fetching upcoming releases:', error);
        });
    }, []);

    return (
        <div className="new-page">
            <div className="left-column">
                <h1>New Releases</h1>
                <p>Here's our top new releases of the month</p>
                <div className="grid-container">
                    {newReleases.map(album => (
                        <Link key={album.id} to={`/album/${album.id}`} className="album-link">
                            <div className="album-card">
                                <img src={album.coverArt} alt={album.title} />
                                <h3>{album.title}</h3>
                                <p>{album.artist}</p>
                            </div>
                        </Link>
                    ))}
                </div>
            </div>
        </div>
    );
}