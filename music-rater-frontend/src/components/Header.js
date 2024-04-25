import React from 'react';
import { Link } from 'react-router-dom';
import "./Header.css";

function Header({ logo }) {
  return (
    <header style={{ backgroundColor: 'black' }}>
      <div className="header-container">
        <div className="logo">
          <Link to="/"><img src={logo} alt="TempoTalk" /></Link>
        </div>
        <div className="search-bar">Search</div>
        <nav>
          <ul style={{ listStyleType: 'none', padding: 0, display: 'flex', gap: '1rem' }}>
            <li><Link to="/new">New</Link></li>
            <li><Link to="/discover">Discover</Link></li>
            <li><Link to="/artist">Artists</Link></li>
            <li><Link to="/profile">Profile</Link></li>
          </ul>
        </nav>
      </div>
    </header>
  );
}

export default Header;
