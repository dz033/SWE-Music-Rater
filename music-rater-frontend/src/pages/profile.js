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


function Profile() {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const userData = useLocation().state
  useEffect(() => {
    console.log(userData)
    const fetchUserData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/users/${userData.id}`);
        setUser(response.data);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    };

    fetchUserData();
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="profile">
      <div className="profile-header">
        <img src={user.profileImage} alt="User" className="profile-photo" />
        <h1>{user.username}</h1>
      </div>
      <div>
        {/* <h2>Reviews</h2> */}
        <ul>
          {<Review id={`users/${userData.id}`}/>}
        </ul>
      </div>
      
      {/* <div className="profile-liked-albums">
        <h2>Liked Albums</h2>
        <ul>
          {user.likedAlbums.map(album => (
            <li key={album.id}>
              <p>Artist: {album.artist}</p>
              <p>Album: {album.album}</p>
            </li>
          ))}
        </ul>
      </div> */}
    </div>
    
    
  );
}

export default Profile;
