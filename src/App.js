import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Navbar from "./Components/navbar";
import NewProfile from "./Pages/newprofile";
import Home from "./Pages/home"
import SignIn from "./Pages/signin";

function App() {
  return (
    <>
      <Router>
        <Navbar />
        <Routes>
          <Route exact path="/newprofile" element={<NewProfile />} />
          <Route exact path="/" element={<Home />} />
          <Route exact path="/signin" element={<SignIn />} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
