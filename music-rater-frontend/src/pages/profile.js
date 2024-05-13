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



const Profile = () => {
  const currentUser = AuthService.getCurrentUser();
  console.log("currentUser object looks like this", currentUser)
  const [user, setUser] = useState(null);

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

  return (
    <div className="profile">
      <div className="profile-header">
        <img src={user.profileImage} alt={user.username} className="profile-photo"/>
        <h1>{user.username}</h1>
        <a href="/profile" onClick={AuthService.logout}>
          Log Out
        </a>
        <div>
          <Review id={`users/${currentUser.id}`}/>
        </div>
      </div>
    </div>
  );
}


export default Profile;
