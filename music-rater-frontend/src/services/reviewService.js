import axios from "axios";
import AuthService from "./authService";

const API_URL = "http://localhost:8080/api/reviews/";

const createReview = async (body, rating, userId, musicId) => {
    const token = AuthService.getCurrentUser()?.accessToken;
    
    // const userData = JSON.parse(localStorage.getItem('user'));
    // const token = userData.token;
    console.log("reviewing user token", token)
    try {
      // Send a POST request to create a review
      const response = await axios.post(API_URL + 'create', {
        body,
        rating,
        userId,
        musicId
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
        return { error: 'Unauthorized. Please log in to submit a review.' };
      }
      // Otherwise, re-throw the error to be caught by the caller
      throw error;
    }
  };


const reviewService = {
  createReview
}

export default reviewService;