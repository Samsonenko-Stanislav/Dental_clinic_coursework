import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { editUser, getSoloUser } from '../store/slice/UserSlice';
import { useParams } from 'react-router-dom';

const EditUsers = () => {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.user.user);
  const params = useParams();
  const [username, setUserName] = useState('');
  const [active, setActive] = useState(false);
  const [employee, setEmployee] = useState('');
  const [changePassword, setChangePassword] = useState(false);
  const [selectedRoles, setSelectedRoles] = useState([]);

  useEffect(() => {
    dispatch(getSoloUser({ newData: { id: params.id } }));
  }, [dispatch, params.id]);

  useEffect(() => {
    if (user?.user) {
      setUserName(user?.user?.username);
      setActive(user?.user?.active);
      setSelectedRoles(user?.user?.roles);
      setEmployee(user?.user?.employeeId);
    }
  }, [user?.user]);

  const handleCheckboxChange = (event) => {
    const role = event.target.name;
    const checked = event.target.checked;

    if (checked) {
      setSelectedRoles((prevRoles) => [...prevRoles, role]);
    } else {
      setSelectedRoles((prevRoles) => prevRoles.filter((prevRole) => prevRole !== role));
    }
  };

  const handlePasswordChange = (event) => {
    setChangePassword(event.target.checked);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    dispatch(editUser({ newData: { active, username, id: params.id, employeeId: employee, roles: selectedRoles } }));
  };

  return (
    <div className="container">
      <div className="col-md-7 col-lg-8">
        <h4 className="mb-3">Редактирование пользователя</h4>
        <form onSubmit={handleSubmit}>
          <div className="row">
            <div className="row col-12 justify-content-between">
              <div className="col-6">
                <label htmlFor="username" className="form-label">
                  Логин
                </label>
                <input type="text" name="username" className="form-control" readOnly id="username" required value={username} onChange={(e) => setUserName(e.target.value)} />
              </div>
              <div className="col-3">
                <input type="checkbox" name="active" id="active" checked={active} onChange={(e) => setActive(e.target.checked)} />
                <label htmlFor="active" className="form-label">
                  Активен
                </label>
              </div>
            </div>
            <div className="col-12">
              <input type="checkbox" name="changePassword" id="changePassword" checked={changePassword} onChange={handlePasswordChange} />
              <label htmlFor="changePassword" className="form-label">
                Сменить пароль
              </label>
              {changePassword && (
                <div className="col-6" id="forChangePassword">
                  <label htmlFor="password" className="form-label">
                    Новый пароль
                  </label>
                  <input type="password" name="password" id="password" className="form-control" />
                </div>
              )}
            </div>
            <div className="col-12">
              {user?.roles &&
                user?.roles.map((role) => (
                  <div key={role}>
                    <input type="checkbox" name={role} id={role} checked={selectedRoles.includes(role)} onChange={handleCheckboxChange} />
                    <label htmlFor={role} className="form-label">
                      {role}
                    </label>
                  </div>
                ))}
            </div>
            {selectedRoles.includes('DOCTOR') && (
              <div id="forEmpl">
                <div className="col-md-5">
                  <label htmlFor="employee" className="form-label">
                    Сотрудник
                  </label>
                  <select className="form-select" id="employee" name="employee" value={employee} onChange={(e) => setEmployee(e.target.value)}>
                    {user?.employees.map((employee) => (
                      <option key={employee.id} value={employee.id}>{`${employee.fullName}`}</option>
                    ))}
                  </select>
                </div>
              </div>
            )}
            <button className="w-100 btn btn-primary btn-lg" type="submit">
              Сохранить
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default EditUsers;
