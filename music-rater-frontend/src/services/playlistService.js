import axios from "axios";
import React, { useState, useEffect } from 'react';
const API_URL = "http://localhost:8080/api/";

const createPlaylist = async (name, description, creatorId) => {
    const token = AuthService.getCurrentUser()?.accessToken;
    
    // const userData = JSON.parse(localStorage.getItem('user'));
    // const token = userData.token;
    console.log("reviewing user token", token)
    try {
      // Send a POST request to create a review
      const response = await axios.post(API_URL + '/playlists/create', {
        name,
        description,
        creatorId,
      },
      {
        headers: {
          Authorization: `Bearer ${token}` // Include the JWT token in the Authorization header
        }
      }
    );
  
      // Return the response data
      return response.data;
    } catch (error) {
      // If the response status is 401, return an error message
      if (error.response && error.response.status === 401) {
        return { error: 'Unauthorized. Please log in to create a playlist.' };
      }
      // Otherwise, re-throw the error to be caught by the caller
      throw error;
    }
  };

const playlistService = {
    createPlaylist
}

export default playlistService;