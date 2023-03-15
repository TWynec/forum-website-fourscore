import React from "react";
import "../styles/signin.css";
import { Link } from "react-router-dom";
import img from "../styles/2955-removebg-preview.png";

function SignIn() {
    return (
        <form action="" method="" className="sign-in">

            <img id={"img"} src={img} />
            <h1>Sign In</h1>

            <label htmlFor="username">Username</label>
            <input type="text" id="username" placeholder="Enter username" />
            <label htmlFor="password">Password</label>
            <input type="password" id="password" placeholder="Enter password" />
            <button type="submit">Sign In</button>

            <Link to="/newprofile">New here? Create an account!</Link>
        </form>
    );
}

export default SignIn;