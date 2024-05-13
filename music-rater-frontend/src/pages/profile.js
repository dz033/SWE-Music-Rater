/*export default function Profile(){
    return(
        <>
            <h1>Profile</h1>        
        </>
    )
}
*/
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import './home.css';
import Review from '../components/Review';
import AuthService from '../services/authService';
import Playlist from '../components/Playlist';
import playlistService from '../services/playlistService';


const Profile = () => {
  const currentUser = AuthService.getCurrentUser();
  console.log("currentUser object looks like this", currentUser)
  const [user, setUser] = useState(null);
  const [PlaylistName, setPlaylistName] = useState('');
  const [PlaylistDescription, setPlaylistDescription] = useState('');

  useEffect(() => {
    if (currentUser && !user) {
      const fetchUserData = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/api/users/${currentUser.id}`);
          setUser(response.data.user);
        } catch (error) {
          console.error('Error fetching user data:', error);
        }
      };
      
      fetchUserData();
    }
  }, [currentUser]); // Make useEffect dependent on currentUser

  if (!currentUser) {
    return <div style={{ color: 'white', fontSize: '48px' }}>Sign in to see user profile</div>;
  }

  // Ensure user is fetched before rendering profile information
  if (!user) {
    return <div>Loading...</div>;
  }

  const handlePlaylistSubmit = async (e) => {
    e.preventDefault();
    try {
      await playlistService.createPlaylist(PlaylistName, PlaylistDescription, currentUser.id);
      // Clear review inputs after submission
      setPlaylistName('');
      setPlaylistDescription('');
      alert('Playlist created successfully!');
    } catch (error) {
      console.error('Error creating playlist:', error);
      alert('Failed to create playlist. Please try again.');
    }
  };

  const handleKeyDown = (e) => {
    if (e.key !== 'Enter') {
      return;
    }
    handlePlaylistSubmit(e);
  };

  return (
    <div className="profile">
      <div className="profile-header">
        <img src={user.profileImage} alt={user.username} className="profile-photo"/>
        <h1>{user.username}</h1>
        <a href="/profile" onClick={AuthService.logout}>
          Log Out
        </a>
      </div>
      <div classname='profile-info'>
          <Review id={`users/${currentUser.id}`}/>  
          <form onSubmit={handlePlaylistSubmit} onKeyDown={handleKeyDown}>
              <input
                placeholder="Enter a name for your playlist"
                type="text"
                value={PlaylistName}
                onChange={(e) => setPlaylistName(e.target.value)}
              />
              <input
                placeholder="Write your thoughts"
                type="text"
                value={PlaylistDescription}
                onChange={(e) => setPlaylistDescription(e.target.value)}
              />
              <button type="submit">Create Playlist</button>
            </form>
          
          <Playlist id={`${currentUser.id}`}/>
        </div>
    </div>
  );
}


export default Profile;
