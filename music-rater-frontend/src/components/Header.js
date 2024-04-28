import React from 'react';
import { Link } from 'react-router-dom';
import "./Header.css";
import { SearchBar } from './SearchBar';

function Header() {
  return (
    <header className="header">
      <div className="header-container">
        <div className="search-bar">
          <SearchBar />
          <div>SearchResults</div>
        </div>
        <nav>
          <ul className="nav-links">
            <li><Link to="/home">Home</Link></li>
            <li><Link to="/new">New</Link></li>
            <li><Link to="/discover">Discover</Link></li>
            <li><Link to="/signin">Signin</Link></li>
          </ul>
        </nav>
      </div>
      
    </header>
  );
}

export default Header;
