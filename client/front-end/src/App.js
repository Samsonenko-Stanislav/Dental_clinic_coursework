import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Footer from './components/Footer.js';
import Home from './pages/Home';
import Login from './authorization/Login';
import Profile from './pages/Profile';
import EditProfile from './pages/EditProfile';
import SignUp from './pages/SignUp';
import Header from './components/Header';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Price from './pages/Price';

const App = () => {

  return (
      <>
        <BrowserRouter>
          <Header/>

          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/profile-edit" element={<EditProfile />} />
            <Route path="/price" element={<Price />} />
            <Route path="/sign-up" element={<SignUp />} />
          </Routes>
          <Footer/>
        </BrowserRouter>
      </>

  );
}

export default App;