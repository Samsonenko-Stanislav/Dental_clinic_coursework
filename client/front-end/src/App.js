import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Footer from './components/Footer.js';
import Home from './pages/Home';
import Login from './authorization/Login';
import Profile from './pages/Profile';
import EditProfile from './pages/EditProfile';
import SignUp from './pages/SignUp';
import Header from './components/Header';
import Price from './pages/Price';
import Appointments from './pages/Appointments';
import Employee from './pages/Employee';
import Users from './pages/Users';
import AppointmentsAdd from './pages/AppoimentsAdd';
import Goods from './pages/Goods';
import { UserContextContextProvider } from './UserContext';
import AddNewUser from './pages/AddNewUser';
import AddEmployee from './pages/AddEmployee';
import EditEmployee from './pages/EditEmployee';
import GoodEdit from './pages/GoodEdit';
import GoodNew from './pages/GoodNew';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import AppointmentsEdit from './pages/AppoimentEdit';

const App = () => {
  return (
    <UserContextContextProvider user={true} role={'user'} >
      <BrowserRouter>
        <Header />
        <div className='container my-4'>
          <Routes>
            <Route path='/' element={<Home />} />
            <Route path='/login' element={<Login />} />
            <Route path='/profile-edit' element={<EditProfile />} />
            <Route path='/price' element={<Price />} />
            <Route path='/sign-up' element={<SignUp />} />
            <Route path='/appointments' element={<Appointments />} />
            <Route path='/appointments/add' element={<AppointmentsAdd />} />
            <Route path='/appointments/edit/:id' element={<AppointmentsEdit />} />
            <Route path='/employee' element={<Employee />} />
            <Route path='/employee/new' element={<AddEmployee />} />
            <Route path='/employee/edit/:id' element={<EditEmployee />} />
            <Route path='/user' element={<Users />} />
            <Route path='/user/new' element={<AddNewUser />} />
            <Route path='/good' element={<Goods />} />
            <Route path='/good/new' element={<GoodNew />} />
            <Route path='/good/edit/:id' element={<GoodEdit />} />
            <Route path='/user/me' element={<Profile />} />
          </Routes>
        </div>
        <Footer />
      </BrowserRouter>
    </UserContextContextProvider>

  );
};

export default App;