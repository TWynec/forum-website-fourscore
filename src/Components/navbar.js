import React from "react";
import { Link } from "react-router-dom";
import img from "../styles/2955-removebg-preview.png"
//import "../styles/App.css";
import "../styles/navbar.css";

function navbar() {
  return (
     <div className="App-header">
         <img className="App-logo" src={img} alt="Website Logo"/>
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
