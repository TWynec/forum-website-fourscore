import React, { useState } from "react";
//import "../styles/"

function Post({ post }) {
  return (
    <div className="post">
      <div className="post-header">
        <img className="post-avatar" src={post.avatar} alt={post.username} />
        <div>
          <span className="post-username">{post.username}</span>
          <span className="post-timestamp">{post.timestamp}</span>
        </div>
      </div>
      <div className="post-content">
        <p>{post.text}</p>
        {post.image && (
          <img className="post-image" src={post.image} alt="Post" />
        )}
      </div>
      <div className="post-actions">
        <button>Like</button>
        <button>Comment</button>
        <button>Share</button>
      </div>
    </div>
  );
}

export default Post;
