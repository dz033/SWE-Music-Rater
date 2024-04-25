import logo from './logo.svg';
import {BrowserRouter, Routes, Route} from 'react-router-dom'
import Layout from './components/Layout';
import Album from './pages/album'
import Artist from './pages/artist'
import Discover from './pages/discover'
import Home from './pages/home'
import Profile from './pages/profile'
import Song from './pages/song'
import Header from './components/Header';
import './App.css';

function App() {
  // const CLIENT_ID = "9ae889ff3843458cbf365530b585f9a3"
  // const REDIRECT_URL = "http://localhost:3000"
  // const AUTH_ENDPOINT = "https://accounts.spotify.com/authorize"
  // const RESPONSE_TYPE = "token"
  return (
    <BrowserRouter>
      <div className="App">
        <Header />
        <Layout />
          <Routes>
            <Route index element={<Home />} />
            <Route path="/home" element={<Home />} />
            <Route path="/album" element={<Album />} />
            <Route path="/artist" element={<Artist />} />
            <Route path="/discover" element={<Discover />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/song" element={<Song />} />
          </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
