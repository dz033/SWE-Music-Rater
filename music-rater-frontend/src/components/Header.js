import React from 'react';
import { Link } from 'react-router-dom';
import "./Header.css";
import { SearchBar } from './SearchBar';
import logo from './logo.png';



function Header() {
  return (
    <header className="header">
      <div className="header-container">
        <SearchBar />
        
          
        <nav>
          <ul className="nav-links">
            
            <li><Link to="/home"><img src={logo} /></Link></li>
            <li><Link to="/new">New</Link></li>
            <li><Link to="/discover">Discover</Link></li>
            <li><Link to="/signin">Sign in</Link></li>
            {/* <li><Link to="/profile">Profile</Link></li> */}
          </ul>
        </nav>
      </div>
      
    </header>
  );
}

export default Header;
