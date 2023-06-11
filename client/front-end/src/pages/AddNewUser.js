import React, { memo, useState } from "react";
import axiosApi from "../axiosApi";

const AddNewUser = () => {
  const [isAdmin, setIsAdmin] = useState(false);
  const [isDoctor, setIsDoctor] = useState(false);
  const [isUser, setIsUser] = useState(false);

  const handleAdminChange = (event) => {
    setIsAdmin(event.target.checked);
  };

  const handleDoctorChange = (event) => {
    setIsDoctor(event.target.checked);
  };

  const handleUserChange = (event) => {
    setIsUser(event.target.checked);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axiosApi.post("/login", {

      });
      console.log(response);
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <div className="container">
      <div className="col-md-7 col-lg-8">
        <h4 className="mb-3">Создать нового пользователя</h4>
        <form onSubmit={handleSubmit}>
          <div className="row">
            <div className="row col-12">
              <div className="col-6">
                <label htmlFor="username" className="form-label">
                  Логин
                </label>
                <input
                  type="text"
                  name="username"
                  className="form-control"
                  id="username"
                  required
                />
              </div>
              <div className="col-6">
                <label htmlFor="password" className="form-label">
                  Пароль
                </label>
                <input
                  type="password"
                  name="password"
                  className="form-control"
                  id="password"
                  required
                />
              </div>
            </div>
            <div className="col">
              <div className="col-md-5">
                <input
                  type="checkbox"
                  name="ADMIN"
                  id="ADMIN"
                  checked={isAdmin}
                  onChange={handleAdminChange}
                />
                <label htmlFor="ADMIN" className="form-label">
                  Администратор (ADMIN)
                </label>
              </div>
              <div className="col-md-5">
                <input
                  type="checkbox"
                  name="DOCTOR"
                  id="DOCTOR"
                  checked={isDoctor}
                  onChange={handleDoctorChange}
                />
                <label htmlFor="DOCTOR" className="form-label">
                  Врач (DOCTOR)
                </label>
              </div>
              <div className="col-md-5">
                <input
                  type="checkbox"
                  name="USER"
                  id="USER"
                  checked={isUser}
                  onChange={handleUserChange}
                />
                <label htmlFor="USER" className="form-label">
                  Пациент (USER)
                </label>
              </div>
            </div>
          </div>
          <div className="row col-12" id="forNotAdmin">
            {isDoctor && (
              <div className="col-6">
                <label htmlFor="fullName" className="form-label">
                  ФИО
                </label>
                <input
                  type="text"
                  name="fullName"
                  className="form-control"
                  required
                  id="fullName"
                />
              </div>
            )}
          </div>
          {isDoctor && (
            <div id="forEmpl">
              <div className="col-6">
                <label htmlFor="jobTitle" className="form-label">
                  Должность
                </label>
                <input
                  type="text"
                  name="jobTitle"
                  className="form-control"
                  required
                  id="jobTitle"
                />
              </div>
              <div className="row col-12">
                <div className="col-6">
                  <label htmlFor="workStart" className="form-label">
                    Старт рабочего дня
                  </label>
                  <input
                    type="time"
                    name="workStart"
                    className="form-control"
                    required
                    id="workStart"
                  />
                </div>
                <div className="col-6">
                  <label htmlFor="workEnd" className="form-label">
                    Конец рабочего дня
                  </label>
                  <input
                    type="time"
                    name="workEnd"
                    className="form-control"
                    required
                    id="workEnd"
                  />
                </div>
                <div className="col-6">
                  <label htmlFor="durationApp" className="form-label">
                    Время приема (минут)
                  </label>
                  <input
                    type="text"
                    name="durationApp"
                    className="form-control"
                    required
                    id="durationApp"
                  />
                </div>
              </div>
            </div>
          )}
          {isUser && (
            <div id="forUser">
              <div className="col-6">
                <label htmlFor="email" className="form-label">
                  e-mail
                </label>
                <input
                  type="email"
                  name="email"
                  className="form-control"
                  id="email"
                />
              </div>
              <div className="row col-12">
                <div className="col-6">
                  <input type="radio" id="MALE" name="gender" value="MALE" />
                  <label htmlFor="MALE">Мужской</label>
                </div>
                <div>
                  <input
                    type="radio"
                    id="FEMALE"
                    name="gender"
                    value="FEMALE"
                  />
                  <label htmlFor="FEMALE">Женский</label>
                </div>
              </div>
            </div>
          )}
          <button className="w-100 btn btn-primary btn-lg my-2" type="submit">
            Создать
          </button>
        </form>
      </div>
    </div>
  );
};

export default memo(AddNewUser);
