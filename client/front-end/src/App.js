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
import { loadFromLocalStorage, saveToLocalStorage } from './utils/localStorage';
import { RequireAuth } from './components/AuthRoute';

const App = () => {
  const user = loadFromLocalStorage('user') || null;

  const isUser = user ? user?.role === 'user' : null;
  const isAdmin = user ? user?.role === 'admin' : null;
  const isDoctor = user ? user?.role === 'doctor' : null;

  return (
    <UserContextContextProvider user={user} role={user?.role}>
      <BrowserRouter>
        <Header />
        <div className='container my-4'>
          <Routes>
            <Route path='/' element={<Home />} />
            <Route path='/login' element={<Login />} />
            <Route path='/price' element={<RequireAuth access={true}><Price /></RequireAuth>} />
            <Route path='/sign-up' element={<SignUp />} />

            <Route path='/user/me' element={<RequireAuth access={isUser}> <Profile /> </RequireAuth>} />
            <Route path='/profile-edit' element={<RequireAuth access={isUser}> <EditProfile /> </RequireAuth>} />

            <Route path='/appointments' element={<RequireAuth> <Appointments /> </RequireAuth>} />
            <Route path='/appointments/add' element={<RequireAuth> <AppointmentsAdd /> </RequireAuth>} />
            <Route path='/appointments/edit/:id' element={<RequireAuth> <AppointmentsEdit /> </RequireAuth>} />
            <Route path='/employee' element={<RequireAuth> <Employee /> </RequireAuth>} />
            <Route path='/employee/new' element={<RequireAuth> <AddEmployee /> </RequireAuth>} />
            <Route path='/employee/edit/:id' element={<RequireAuth> <EditEmployee /> </RequireAuth>} />
            <Route path='/user' element={<RequireAuth> <Users /> </RequireAuth>} />
            <Route path='/user/new' element={<RequireAuth> <AddNewUser /> </RequireAuth>} />
            <Route path='/good' element={<RequireAuth> <Goods /> </RequireAuth>} />
            <Route path='/good/new' element={<RequireAuth> <GoodNew /> </RequireAuth>} />
            <Route path='/good/edit/:id' element={<RequireAuth> <GoodEdit /> </RequireAuth>} />
          </Routes>
        </div>
        <Footer />
      </BrowserRouter>
    </UserContextContextProvider>

  );
};

export default App;