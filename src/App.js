import React, { useState } from 'react';
import './styles/App.css';
function Post({ post }) {
  return (
    <div>
      <b>{post.username}</b>
      <p>{post.text}</p>
    </div>
  );
}

function PostInput({ profile, onSubmit }) {
  const [postText, setPostText] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();
    onSubmit({ username: profile.name, text: postText });
    setPostText('');
  };

  return (
    <form onSubmit={handleSubmit}>
      <textarea value={postText} onChange={(event) => setPostText(event.target.value)} />
      <button type="submit">Post</button>
    </form>
  );
}

function ProfileForm({ profile, onProfileSubmit }) {
  const [username, setUsername] = useState(profile.name);

  const handleSubmit = (event) => {
    event.preventDefault();
    onProfileSubmit({ name: username });
  };

  return (
    <form onSubmit={handleSubmit}>
    </form>
  );
}

function SignInForm({ onSignIn }) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();
    onSignIn({ username, password });
  };

  return (
    <form onSubmit={handleSubmit}>
      <input value={username} onChange={(event) => setUsername(event.target.value)} />
      <input value={password} onChange={(event) => setPassword(event.target.value)} type="password" />
      <button type="submit">Sign In</button>
    </form>
  );
}

function App() {
  const [signedIn, setSignedIn] = useState(false);
  const [profile, setProfile] = useState({ name: '' });
  const [posts, setPosts] = useState([]);

  const handleSignIn = (credentials) => {
    setProfile({ name: credentials.username });
    setSignedIn(true);
  };

  const handleProfileSubmit = (profile) => {
    setProfile(profile);
  };

  const handleSubmit = (post) => {
    setPosts([post, ...posts]);
  };

  return (
    <div>
      {signedIn ? (
        <>
          <ProfileForm profile={profile} onProfileSubmit={handleProfileSubmit} />
          <PostInput profile={profile} onSubmit={handleSubmit} />
          {posts.map((post) => (
            <Post key={post.text} post={post} />
          ))}
        </>
      ) : (
        <SignInForm onSignIn={handleSignIn} />
      )}
    </div>
  );
}

export default App;
