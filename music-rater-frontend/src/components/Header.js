import React from 'react';
import { Link } from 'react-router-dom';
import "./Header.css";


function Header() {
  return (
    <header className="header">
      <div className="header-container">
        <div className="search-bar">Search</div>
        <nav>
          <ul className="nav-links">
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
