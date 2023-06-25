import React, { memo, useMemo, useState } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Footer from "./components/Footer.js";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Profile from "./pages/Profile";
import SignUp from "./pages/SignUp";
import Header from "./components/Header";
import Price from "./pages/Price";
import Appointments from "./pages/Appointments";
import Employee from "./pages/Employee";
import Users from "./pages/Users";
import AppointmentsAdd from "./pages/AppoimentsAdd";
import Goods from "./pages/Goods";
import { UserContextContextProvider } from "./context/UserContext";
import AddNewUser from "./pages/AddNewUser";
import AddEmployee from "./pages/AddEmployee";
import EditEmployee from "./pages/EditEmployee";
import GoodEdit from "./pages/GoodEdit";
import GoodNew from "./pages/GoodNew";
import AppointmentsEdit from "./pages/AppoimentEdit";
import { RequireAuth } from "./components/AuthRoute";
import EditUsers from "./pages/EditUsers";
import "bootstrap/dist/css/bootstrap.min.css";
import Spinner from "./components/Spinner";
import { useSelector } from "react-redux";
import { NoLoginRoute } from "./components/NoLoginRoute";
import { notification } from "antd";
import NotFound from "./components/NotFoundComponent/NotFound";

export const showNotification = (type, text, title) => {
  if(notification[type]){
    notification[type]({
      message: title,
      description: text,
      className: 'notification-border',
    });
  }
};

const App = () => {
  const user = useSelector((state) => state.user);
  const [loading, setLoading] = useState(false);

  const isUser = useMemo(() => (user ? user?.role.includes('USER') : null), [user]);
  const isAdmin = useMemo(() => (user ? user?.role.includes('ADMIN') : null), [user]);
  const isDoctor = useMemo(() => (user ? user?.role.includes('DOCTOR') : null), [user]);

  return (
    <UserContextContextProvider user={user} role={user?.role} setLoading={setLoading}>
      <BrowserRouter >
        <Header />
        <div className="container overflow-auto overflow-block">
          <Routes>
            <Route path="/" element={<Home />} />
              <Route path="/*" element={<NotFound />} />
            <Route
              path="/login"
              element={
                <NoLoginRoute>
                  <Login />
                </NoLoginRoute>
              }
            />
            <Route path="/price" element={<Price />} />
            <Route
              path="/sign-up"
              element={
                <NoLoginRoute>
                  <SignUp />
                </NoLoginRoute>
              }
            />
            <Route
              path="/user/me"
              element={
                <RequireAuth access={isUser}>
                  <Profile />
                </RequireAuth>
              }
            />
            <Route
              path="/appointments/add"
              element={
                <RequireAuth access={isUser}>
                  {' '}
                  <AppointmentsAdd />
                </RequireAuth>
              }
            />

            <Route
              path="/appointments"
              element={
                <RequireAuth access={isUser || isDoctor}>
                  <Appointments />
                </RequireAuth>
              }
            />
            <Route
              path="/appointments/edit/:id"
              element={
                <RequireAuth access={isUser || isDoctor}>
                  <AppointmentsEdit />
                </RequireAuth>
              }
            />

            <Route
              path="/user"
              element={
                <RequireAuth access={isAdmin}>
                  <Users />
                </RequireAuth>
              }
            />
            <Route
              path="/user/new"
              element={
                <RequireAuth access={isAdmin}>
                  <AddNewUser />
                </RequireAuth>
              }
            />

            <Route
              path="/user/edit/:id"
              element={
                <RequireAuth access={isAdmin}>
                  <EditUsers />
                </RequireAuth>
              }
            />

            <Route
              path="/employee"
              element={
                <RequireAuth access={isAdmin}>
                  <Employee />
                </RequireAuth>
              }
            />
            <Route
              path="/employee/new"
              element={
                <RequireAuth access={isAdmin}>
                  <AddEmployee />
                </RequireAuth>
              }
            />
            <Route
              path="/employee/edit/:id"
              element={
                <RequireAuth access={isAdmin}>
                  <EditEmployee />
                </RequireAuth>
              }
            />
            <Route
              path="/good"
              element={
                <RequireAuth access={isAdmin}>
                  <Goods />
                </RequireAuth>
              }
            />
            <Route
              path="/good/new"
              element={
                <RequireAuth access={isAdmin}>
                  <GoodNew />
                </RequireAuth>
              }
            />
            <Route
              path="/good/edit/:id"
              element={
                <RequireAuth access={isAdmin}>
                  <GoodEdit />
                </RequireAuth>
              }
            />
          </Routes>
        </div>
        {loading && <Spinner />}
        <Footer />
      </BrowserRouter>
    </UserContextContextProvider>
  );
};

export default memo(App);
