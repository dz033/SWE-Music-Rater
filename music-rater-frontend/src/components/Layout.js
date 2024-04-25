import React from 'react';
import './Layout.css';

function Layout({ children }) {
  return (
    <div className="layout-container">
      <header>
        <h1 className="header-text">Welcome to TempoTalk</h1>
      </header>
      <div className="header-content">
        {children}
      </div>
    </div>
  );
}


export default Layout;

