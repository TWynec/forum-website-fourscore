import React from "react";
import "../styles/newprofile.css";

function NewProfile() {
  return (
    <form action="nowhere.html" method="POST">
      <div className="newProfile">
        <p>
          <label htmlFor="username">UserName:</label>
          <input type="text" id="username" name="username" />
        </p>

        <p>
          <label htmlFor="password">Password:</label>
          <input type="text" id="password" name="password" />
        </p>

        <p>
          <label htmlFor="bio">Bio:</label>
          <textarea id="bio" name="bio" rows="7" cols="49"></textarea>
        </p>

        <button type="submit">Create Account</button>
      </div>
    </form>
  );
}

export default NewProfile;
