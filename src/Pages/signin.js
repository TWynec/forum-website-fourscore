import React from "react";
import "../styles/signin.css";
import { Link } from "react-router-dom";

function SignIn() {
    return (
        <div className="sign-in">
            <h1>Sign In</h1>
            <form>
                <label htmlFor="username">Username</label>
                <input type="text" id="username" placeholder="Enter username" />
                <label htmlFor="password">Password</label>
                <input type="password" id="password" placeholder="Enter password" />
                <button type="submit">Sign In</button>
            </form>
            <Link to="/newprofile">New? Create an account!</Link>
        </div>
    );
}

export default SignIn;