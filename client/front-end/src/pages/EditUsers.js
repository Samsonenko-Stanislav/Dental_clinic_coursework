import React, { useEffect, useState } from "react";
import axiosApi from "../axiosApi";

const EditUsers = () => {
  const [user, setUser] = useState({});
  const [roles, setRoles] = useState([]);
  const [employees, setEmployees] = useState([]);
  const [message, setMessage] = useState("");
  const [changePassword, setChangePassword] = useState(false);
  const [selectedRoles, setSelectedRoles] = useState([]);
  const [selectedEmployee, setSelectedEmployee] = useState("");

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const userResponse = await axiosApi.get("/api/user");
      const rolesResponse = await axiosApi.get("/api/roles");
      const employeesResponse = await axiosApi.get("/api/employees");

      setUser([]);
      setRoles([]);
      setEmployees([]);
    } catch (error) {
      console.log(error);
    }
  };

  const handleCheckboxChange = (event) => {
    const role = event.target.name;
    const checked = event.target.checked;

    if (checked) {
      setSelectedRoles((prevRoles) => [...prevRoles, role]);
    } else {
      setSelectedRoles((prevRoles) =>
        prevRoles.filter((prevRole) => prevRole !== role)
      );
    }
  };

  const handleEmployeeSelect = (event) => {
    setSelectedEmployee(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setChangePassword(event.target.checked);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      // Perform your submit logic here using axios or any other library
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className="container">
      <div className="col-md-7 col-lg-8">
        <h4 className="mb-3">Редактирование пользователя</h4>
        {message && <div>{message}</div>}
        <form onSubmit={handleSubmit}>
          <div className="row">
            <div className="row col-12 justify-content-between">
              <div className="col-6">
                <label htmlFor="username" className="form-label">
                  Логин
                </label>
                <input
                  type="text"
                  name="username"
                  className="form-control"
                  readOnly
                  id="username"
                  required
                  value={user.username}
                />
              </div>
              <div className="col-3">
                <input
                  type="checkbox"
                  name="active"
                  id="active"
                  checked={user.active}
                  onChange={handleCheckboxChange}
                />
                <label htmlFor="active" className="form-label">
                  Активен
                </label>
              </div>
            </div>
            <div className="col-12">
              <input
                type="checkbox"
                name="changePassword"
                id="changePassword"
                checked={changePassword}
                onChange={handlePasswordChange}
              />
              <label htmlFor="changePassword" className="form-label">
                Сменить пароль
              </label>
              {changePassword && (
                <div className="col-6" id="forChangePassword">
                  <label htmlFor="password" className="form-label">
                    Новый пароль
                  </label>
                  <input
                    type="password"
                    name="password"
                    id="password"
                    className="form-control"
                  />
                </div>
              )}
            </div>
            <div className="col-12">
              {roles &&
                roles.map((role) => (
                  <div key={role}>
                    <input
                      type="checkbox"
                      name={role}
                      id={role}
                      checked={selectedRoles.includes(role)}
                      onChange={handleCheckboxChange}
                    />
                    <label htmlFor={role} className="form-label">
                      {role}
                    </label>
                  </div>
                ))}
            </div>
            {selectedRoles.includes("DOCTOR") && (
              <div id="forEmpl">
                <div className="col-md-5">
                  <label htmlFor="employee" className="form-label">
                    Сотрудник
                  </label>
                  <select
                    className="form-select"
                    id="employee"
                    name="employee"
                    value={selectedEmployee}
                    onChange={handleEmployeeSelect}
                  >
                    {employees.map((employee) => (
                      <option
                        key={employee.id}
                        value={employee.id}
                      >{`${employee.id}:${employee.fullName}`}</option>
                    ))}
                  </select>
                </div>
              </div>
            )}
            <button className="w-100 btn btn-primary btn-lg" type="submit">
              Сохранить
            </button>
          </div>
          <input type="hidden" name="userId" value={user.id} />
        </form>
      </div>
    </div>
  );
};

export default EditUsers;
