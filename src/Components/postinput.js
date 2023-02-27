import React, { useState } from "react";

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
      <div className="post-input-avatar">
        <img src={profile.avatar} alt={profile.name} />
      </div>
      <form onSubmit={handleSubmit} className="post-input-form">
        <textarea
          value={postText}
          onChange={(event) => setPostText(event.target.value)}
          placeholder={`Say something`}
        />
        <button type="submit">Post</button>
      </form>
    </div>
  );
}

export default PostInput;
