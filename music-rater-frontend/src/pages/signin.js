
import "./signin.css"
import React, { useState, useEffect } from 'react';
const API_DIR = "http://localhost:8080/";
export default function Signin() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const handleSignIn = async () => {
    try {
      console.log("test");
      const response = await fetch(API_DIR + 'api/auth/signin', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        throw new Error('Sign-in failed');
      }

      // Assuming the server responds with some data upon successful sign-in
      const data = response.json()
      
      console.log("look here: ", data);
      //console.log(JSON.stringify(data));
      // Handle successful sign-in, e.g., store user data in state or local storage
      console.log('Sign-in successful!', data);
    } catch (error) {
      setError('Sign-in failed. Please check your credentials and try again.');
      console.error('Error signing in:', error);
    }
    };
    return (
      <div>
        <h1>Sign in</h1>
        <form onSubmit={handleSignIn()}>
          <div>
            <label>Username:</label>
            <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
          </div>
          <div>
            <label>Password:</label>
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
          </div>
          <button type="submit">Sign In</button>
        </form>
      </div>
    );

  }


