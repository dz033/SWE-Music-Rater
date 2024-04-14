import logo from './logo.svg';
import {BrowserRouter, Routes, Route} from 'react-router-dom'
import Album from './pages/album'
import Artist from './pages/artist'
import Discover from './pages/discover'
import Home from './pages/home'
import Profile from './pages/profile'
import Song from './pages/song'
import './App.css';

function App() {
  // const CLIENT_ID = "9ae889ff3843458cbf365530b585f9a3"
  // const REDIRECT_URL = "http://localhost:3000"
  // const AUTH_ENDPOINT = "https://accounts.spotify.com/authorize"
  // const RESPONSE_TYPE = "token"

  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route index element={<Home />} />
          <Route path="/home" element={<Home />} />
          <Route path="/album" element={<Album />} />
          <Route path="/artist" element={<Artist />} />
          <Route path="/discover" element={<Discover />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/song" element={<Song />} />
          

        </Routes>
      </BrowserRouter>
      <header className="App-header">
        <h1>TempoTalk</h1>
          
      </header>
    </div>
  )
    
}

export default App;
