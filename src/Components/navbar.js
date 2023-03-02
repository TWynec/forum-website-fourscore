import React from "react";
import { Link } from "react-router-dom";
import "../styles/App.css";
import "../styles/navbar.css";

function navbar() {
  return (
     <div class="App-header">
       <img class="App-logo" src="2955-removebg-preview.png" alt="Website Logo">
     <nav>
      <ul>
        <li>
          <Link to="/">Home</Link>
        </li>
        <li>
          <Link to="/new-profile">New Profile</Link>
        </li>
      </ul>
    </nav>
     </div>
  );
}

export default navbar;
