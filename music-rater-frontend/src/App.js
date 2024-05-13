
import React, { useState, useEffect } from "react";
// import logo from './logo.svg';
import {BrowserRouter, Routes, Route, Link} from 'react-router-dom';
import Layout from './components/Layout';
import AlbumPage from './pages/AlbumPage';
import Artist from './pages/artist';
import Discover from './pages/discover';
import Home from './pages/home';
import Profile from './pages/profile';
import New from './pages/new';
import Header from './components/Header';
import './App.css';
import Signin from './pages/signin';

import AuthService from './services/authService';
import EventBus from "./services/eventBus";
import SongPage from "./pages/SongPage";

// import Signin from './signin';

const App = () => {
  // const CLIENT_ID = "9ae889ff3843458cbf365530b585f9a3"
  // const REDIRECT_URL = "http://localhost:3000"
  // const AUTH_ENDPOINT = "https://accounts.spotify.com/authorize"
  // const RESPONSE_TYPE = "token"
  const [currentUser, setCurrentUser] = useState(undefined);


  useEffect(() => {
    const user = AuthService.getCurrentUser();
    if (user) {
      setCurrentUser(user);
      // setShowModeratorBoard(user.roles.includes("ROLE_MODERATOR"));
      // setShowAdminBoard(user.roles.includes("ROLE_ADMIN"));
    }

    EventBus.on("logout", () => {
      logOut();
    });

    return () => {
      EventBus.remove("logout");
    };
  }, []);

  const logOut = () => {
    AuthService.logout();
    // setShowModeratorBoard(false);
    // setShowAdminBoard(false);
    setCurrentUser(undefined);
  };
  
  return (
    <BrowserRouter>
      <div className="App">
        <Header />
        <Layout />
          <Routes>
            <Route index element={<Home />} />
            <Route path="/home" element={<Home />} />
            <Route path="/album/:id" element={<AlbumPage />} />
            <Route path="/artist" element={<Artist />} />
            <Route path="/discover" element={<Discover />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/new" element={<New />} />
            <Route path="/signin" element={<Signin/>} />
            <Route path="/song/:id" element={<SongPage/>}/>
          </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
