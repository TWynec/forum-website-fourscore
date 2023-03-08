import React from "react";
import { Link } from "react-router-dom";
import img from "../styles/2955-removebg-preview.png"
import "../styles/navbar.css";

function navbar() {
  return (
    <div className="App-header">
      <img className="App-logo" src={img} alt="Website Logo" />
      <p className="App-title">Knock off Twitter</p>
      <nav>
        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/newprofile">Profile</Link>
          </li>
          <li>
            <Link to="/signin">Sign~In</Link>
          </li>
        </ul>
      </nav>
    </div>
  );
}

export default navbar;
