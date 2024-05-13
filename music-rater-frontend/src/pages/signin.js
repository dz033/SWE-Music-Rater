
import "./signin.css"
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import AuthService from "../services/authService";
import "./home.css"

const API_DIR = "http://localhost:8080/";
export default function Signin() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSignIn = async (e) => {
    e.preventDefault()
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
      const data = await response.json()
      localStorage.setItem('token', response.token);
      AuthService.login(username, password).then(
        () => {
          navigate("/profile");
          window.location.reload();
        }
      );
      //console.log(JSON.stringify(data));
      // Handle successful sign-in, e.g., store user data in state or local storage
      console.log('Sign-in successful!', data);
      navigate("/profile", {state: data});

    } catch (error) {
      setError('Sign-in failed. Please check your credentials and try again.');
      console.error('Error signing in:', error);
    }
    };

    const handleKeyDown = (e) =>{
      if (e.key != "Enter"){
        return
      }
    }
    return (
      <div class="signin-object">
        <h1>Sign in</h1>
        <form onSubmit={handleSignIn} onKeyDown={handleKeyDown} >
          <div>
            <input placeholder='Enter Username' type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
          </div>
          <div>
            <input placeholder='Enter Password' type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
          </div>
          <button type="submit">Sign In</button>
        </form>
      </div>
    );

  }


