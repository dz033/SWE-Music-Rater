import axios from "axios";
import AuthService from "./authService";

const API_URL = "http://localhost:8080/api/songs/";

const getSongByTitle = async (title) => {
    const token = AuthService.getAccessToken();
    try {
      // Send a POST request to create a review
      const response = await axios.get(API_URL + "title/" + title, {
        title
      },
      {
        headers: {
          Authorization: `Bearer ${token}` // Include the JWT token in the Authorization header
        }
      }
    );
  
      // Return the response data
      
      const firstSong = response.data[0]
      console.log("SONG BY TITLE: ", firstSong.id)
      return firstSong.id;
    } catch (error) {
      // If the response status is 401, return an error message
      if (error.response && error.response.status === 401) {
        return { error: 'Unauthorized. Please log in to submit a review.' };
      }
      // Otherwise, re-throw the error to be caught by the caller
      throw error;
    }
  };

  const getSongByID = async (id) => {
    //const token = AuthService.getCurrentUser()?.accessToken;
    const token = AuthService.getAccessToken();

    try {
      // Send a POST request to create a review
      const response = await axios.get(API_URL + id, {
        id
      },
    );
  
      // Return the response data
      
      const firstSong = response.data.song
      console.log("SONG BY id: ", firstSong)
      return firstSong;
    } catch (error) {
      // If the response status is 401, return an error message
      if (error.response && error.response.status === 401) {
        return { error: 'Unauthorized. Please log in to submit a review.' };
      }
      // Otherwise, re-throw the error to be caught by the caller
      throw error;
    }
  };
const songService = {
  getSongByTitle,
  getSongByID
}

export default songService;