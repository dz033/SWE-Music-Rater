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

function Profile() {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const response = await axios.get('/api/user/profile');
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
        <img src={user.photoUrl} alt="User" className="profile-photo" />
        <h1>{user.name}</h1>
      </div>
      <div className="profile-reviews">
        <h2>Reviews</h2>
        <ul>
          {user.reviews.map(review => (
            <li key={review.id}>
              <p>{review.title}</p>
              <p>Artist: {review.artist}</p>
              <p>Album: {review.album}</p>
            </li>
          ))}
        </ul>
      </div>
      <div className="profile-liked-albums">
        <h2>Liked Albums</h2>
        <ul>
          {user.likedAlbums.map(album => (
            <li key={album.id}>
              <p>Artist: {album.artist}</p>
              <p>Album: {album.album}</p>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default Profile;
