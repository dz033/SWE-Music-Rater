import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

const register = (username, email, password) => {
  return axios.post(API_URL + "signup", {
    username,
    email,
    password,
  });
};

const login = (username, password) => {
  return axios
    .post(API_URL + "signin", {
      username,
      password,
    })
    .then((response) => {
      if (response.data.username) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }

      return response.data;
    });
};

const logout = () => {
  localStorage.removeItem("user");
  return axios.post(API_URL + "signout").then((response) => {
    return response.data;
  });
};

const getCurrentUser = () => {
  //console.log("current user", JSON.parse(localStorage.getItem("user")))
  return JSON.parse(localStorage.getItem("user"));
  
};

const getAccessToken = () => {
  const user = JSON.parse(localStorage.getItem("user"));
  // console.log("THIS IS AN USER", user)
    
  console.log("Access Token:", user.token);
  return user.token;

}

const AuthService = {
  register,
  login,
  logout,
  getCurrentUser,
  getAccessToken
}

export default AuthService;