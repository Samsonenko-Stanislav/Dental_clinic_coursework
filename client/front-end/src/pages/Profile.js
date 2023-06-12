import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { getUser, updateUser } from '../store/slice/UserSlice';

const Profile = () => {
  const dispatch = useDispatch();
  const [user, setUser] = useState({});
  const [changePassword, setChangePassword] = useState(false);

  useEffect(() => {
    dispatch(getUser({}));
  }, [dispatch]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    dispatch(updateUser({ newData: user }));
  };

  const handleCheckboxChange = (e) => {
    setChangePassword(e.target.checked);
  };

  const handleInputChange = (e) => {
    setUser({
      ...user,
      [e.target.name]: e.target.value,
    });
  };

  // const togglePassword = () => {
  //   setChangePassword(!changePassword);
  // };

  const handleRemoveChangePassword = () => {
    setUser({
      ...user,
      password: '',
    });
  };

  return (
    <div>
      <h4 className="mb-3">Профиль</h4>
      <form onSubmit={handleSubmit}>
        <div className="row">
          <div className="row col-12">
            <div className="col-6">
              <label htmlFor="username" className="form-label">
                Логин
              </label>
              <input type="text" name="username" className="form-control" id="username" required value={user.username || ''} onChange={handleInputChange} />
            </div>
          </div>
          <div className="col-12">
            <input type="checkbox" name="changePassword" id="changePassword" checked={changePassword} onChange={handleCheckboxChange} />
            <label htmlFor="changePassword" className="form-label">
              Сменить пароль
            </label>
            <div className="col-6" id="forChangePassword" style={{ display: changePassword ? 'block' : 'none' }}>
              <label htmlFor="password" className="form-label">
                Новый пароль
              </label>
              <input type="password" name="password" id="password" className="form-control" value={user.password || ''} onChange={handleInputChange} />
            </div>
          </div>
          <div className="row col-12">
            <div className="col-6">
              <label htmlFor="fullName" className="form-label">
                ФИО
              </label>
              <input type="text" name="fullName" className="form-control" id="fullName" required value={user.fullName || ''} onChange={handleInputChange} />
            </div>
          </div>
          <div className="row">
            <div className="row col-12">
              <div className="col-6">
                <label htmlFor="email" className="form-label">
                  Email
                </label>
                <input type="text" name="email" className="form-control" id="email" value={user.email || ''} onChange={handleInputChange} />
              </div>
            </div>
          </div>
          <div>
            <br />
            <p>Пол:</p>
            <input type="radio" id="male" name="gender" value="male" checked={user.gender === 'male'} onChange={handleInputChange} className="mx-2" />
            <label htmlFor="male">Мужской</label>
            <input type="radio" id="female" name="gender" value="female" checked={user.gender === 'female'} onChange={handleInputChange} className="mx-2" />
            <label htmlFor="FEMALE">Женский</label>
          </div>
          <button className="w-100 btn btn-primary btn-lg  my-" type="submit">
            Сохранить
          </button>
        </div>
        <input type="hidden" name="userId" value={user.id || ''} />
      </form>
      {changePassword && (
        <button className="btn btn-secondary my-4" onClick={handleRemoveChangePassword}>
          Отменить смену пароля
        </button>
      )}
    </div>
  );
};

export default Profile;
