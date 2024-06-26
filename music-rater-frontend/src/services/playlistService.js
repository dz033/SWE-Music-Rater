import axios from "axios";
import React, { useState, useEffect } from 'react';
import AuthService from "./authService";
import songService from "./songService";
const API_URL = "http://localhost:8080/api/";



const createPlaylist = async (name, description, creatorId) => {
    //const token = AuthService.getCurrentUser()?.accessToken;
    const token = AuthService.getAccessToken();
    // const userData = JSON.parse(localStorage.getItem('user'));
    // const token = userData.token;
    console.log("reviewing user token", token)
    try {
      // Send a POST request to create a review
      const response = await axios.post(API_URL + 'playlists/create', {
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
      console.log("cr playlist response data", response.data)
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

  const addSongByName = async (songName, playlistId ) => {
    //const token = AuthService.getCurrentUser()?.accessToken;
    const token = AuthService.getAccessToken();
    const songId = await songService.getSongByTitle(songName);
    // const userData = JSON.parse(localStorage.getItem('user'));
    // const token = userData.token;
    console.log("ADD SONG TOKEN", token)
    console.log("PLAYLIST ID", playlistId)
    console.log("song id: ", songId)
    try {
      // Send a POST request to create a review
      const response = await axios.put(API_URL + 'playlists/add', {
        songId: songId, 
        playlistId: playlistId
      },
      {
        headers: {
          Authorization: `Bearer ${token}` // Include the JWT token in the Authorization header
        }
      }
    );
  
      // Return the response data
      console.log("add song to playlist response data", response.data)
      return response.data;
    } catch (error) {
      // If the response status is 401, return an error message
      if (error.response && error.response.status === 401) {
        return { error: 'Unauthorized. Please log in.' };
      }
      // Otherwise, re-throw the error to be caught by the caller
      throw error;
    }
  };

const playlistService = {
    createPlaylist,
    addSongByName
}

export default playlistService;