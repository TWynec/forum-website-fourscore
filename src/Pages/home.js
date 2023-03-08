import React, { useState } from "react";
import "../styles/App.css";
import Post from "../Components/post";
import PostInput from "../Components/postinput";

function Home() {
  const [signedIn, setSignedIn] = useState(true);
  const [profile, setProfile] = useState({
    name: "Trace Wynecoop",
    avatar:
      "https://www.planetware.com/wpimages/2020/02/france-in-pictures-beautiful-places-to-photograph-eiffel-tower.jpg",
  });
  const [posts, setPosts] = useState([]);

  const handleProfileSubmit = (profile) => {
    setProfile(profile);
  };

  const handleSubmit = (post) => {
    setPosts([post, ...posts]);
  };

  return (
    <>
      <div className="post-list-container">
        {signedIn ? (
          <>
            <PostInput profile={profile} onSubmit={handleSubmit} />
            {posts.map((post) => (
              <Post key={post.text} post={post} />
            ))}
          </>
        ) : (
          <>
            <h1>Update sign in</h1>
          </>
        )}
      </div>
    </>
  );
}

export default Home;
