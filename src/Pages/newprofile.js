import React from "react";
import "../styles/newprofile.css";

function NewProfile() {
  return (
    <form action="" method="" className="newProfile">
      <div>
        <p>
          <label htmlFor="username">UserName:</label>
          <input type="text" id="username" name="username" placeholder="Username"/>
        </p>

        <p>
          <label htmlFor="password">Password:</label>
          <input type="text" id="password" name="password" placeholder="Password"/>
        </p>

        <p>
          <label htmlFor="bio">Bio:</label>
          <textarea id="bio" name="bio" rows="7" cols="49" ></textarea>
        </p>

        <button id="newAccount" type="submit">Create Account</button>
      </div>
    </form>
  );
}

export default NewProfile;
