import React, {useContext, useEffect, useState} from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { updateUser, getSoloUser, nullifyUser } from '../store/slice/UserSlice';
import { useNavigate, useParams } from 'react-router-dom';
import NotFound from "../components/NotFoundComponent/NotFound";
import {showNotification} from "../App";
import {UserContext} from "../context/UserContext";

const EditUsers = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const user = useSelector((state) => state.user.user);
  const params = useParams();
  const [username, setUserName] = useState('');
  const [active, setActive] = useState(false);
  const [employee, setEmployee] = useState('');
  const [changePassword, setChangePassword] = useState(false);
  const [selectedRoles, setSelectedRoles] = useState([]);
  const [email, setEmail] = useState('');
  const [gender, setGender] = useState('');
  const [fullName, setFullName] = useState('');
  const [password, setPassword] = useState('');
  const { setLoading } = useContext(UserContext);

  useEffect(() => {
    dispatch(getSoloUser({ newData: { id: params.id } }));
  }, [dispatch, params.id]);

  useEffect(() => {
    if (user?.user) {
      setUserName(user?.user?.username);
      setActive(user?.user?.active);
      setSelectedRoles(user?.user?.roles);
      if (user.user.employee != null){
        setEmployee(user?.user?.employeeId);
      }
      else setEmployee("");

      if (user.user.outpatientCard) {
        setEmail(user.user.outpatientCard.email);
        setGender(user.user.outpatientCard.gender);
        setFullName(user.user.outpatientCard.fullName);
      }
    }
  }, [user?.user]);

  useEffect(() => {
    return () => {
      dispatch(nullifyUser());
    };
  }, [dispatch]);

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
    setLoading(true);
    event.preventDefault();
    const response = await dispatch(updateUser({ newData: { active, username, id: params.id, employeeId: employee, roles: selectedRoles, email, gender, fullName, password } }));
    setPassword('');
    setLoading(false);
    if (response?.type?.includes('fulfilled')){
      navigate('/user');
      showNotification('success', 'Вы успешно изменили пользователя', 'Изменение пользователя');
    }
  };
if (user?.user){
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
                <input type="text" name="username" className="form-control" id="username" required value={username} onChange={(e) => setUserName(e.target.value)} />
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
                  <input type="password" required name="password" id="password" className="form-control" password={password} onChange={(e) => setPassword(e.target.value)} />
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
                    <option key={0} value={0}>{``}</option>
                    {user?.employees.map((employee) => (
                      <option key={employee.id} value={employee.id}>{`${employee.fullName}`}</option>
                    ))}
                  </select>
                </div>
              </div>
            )}

              {selectedRoles.includes('USER') && (
                  <div id="forUser">
                    <div className="col-6">
                      <label htmlFor="email" className="form-label">
                        e-mail
                      </label>
                      <input required type="email" name="email" className="form-control" id="email" value={email} onChange={(e) => setEmail(e.target.value)} />
                    </div>
                    <div className="row col-12">
                      <div className="col-6">
                        <input type="radio" id="MALE" name="gender" value={'MALE'} checked={gender === 'MALE'} onChange={() => setGender('MALE')} />
                        <label htmlFor="MALE">Мужской</label>
                      </div>
                      <div>
                        <input type="radio" id="FEMALE" name="gender" value={'FEMALE'} checked={gender === 'FEMALE'} onChange={() => setGender('FEMALE')} />
                        <label htmlFor="FEMALE">Женский</label>
                      </div>
                    </div>

                    <div className="col-6">
                      <label htmlFor="fullName" className="form-label">
                        ФИО
                      </label>
                      <input type="text" name="fullName" className="form-control" required id="fullName" value={fullName} onChange={(e) => setFullName(e.target.value)} />
                    </div>
                  </div>
              )}
              <button className="w-100 btn btn-primary btn-lg my-4" type="submit">
                Сохранить
              </button>
            </div>
          </form>
        </div>
      </div>
  );
}
else{
  return <NotFound/>
}

};

export default EditUsers;
