import React, { useState } from "react";
import "./styles/App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./Components/navbar";
import Post from "./Components/post";
import PostInput from "./Components/postinput";

function App() {
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
      <Router>
        <Navbar />
        <Routes>
          <Route exact path="/normal_redirect" render={() => {window.location.href="./Pages/userprofile.html"}} />
        </Routes>
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
      </Router>
    </>
  );
}

export default App;
