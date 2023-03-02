import React, { useState } from "react";
import "../styles/postinput.css";

function PostInput({ profile, onSubmit }) {
  const [postText, setPostText] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();
    onSubmit({
      username: profile.name,
      text: postText,
      avatar: profile.avatar,
    });
    setPostText("");
  };

  return (
    <div className="post-input-container">
      <div className="post-avatar">
        <img src={profile.avatar} alt={profile.name} />
      </div>
      <form onSubmit={handleSubmit} className="post-input-form">
        <textarea
          value={postText}
          onChange={(event) => setPostText(event.target.value)}
          placeholder={`Say something`}
        />
        <button id="postbtn" type="submit">Post</button>
      </form>
    </div>
  );
}

export default PostInput;
