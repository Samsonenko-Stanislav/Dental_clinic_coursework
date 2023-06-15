import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getUser, updateProfile } from '../store/slice/UserSlice';
import { useNavigate } from 'react-router-dom';
import { showNotification } from '../App';

const Profile = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [user, setUser] = useState({});
  const [changePassword, setChangePassword] = useState(false);
  const profile = useSelector((state) => state.user.profile);
  const [gender, setGender] = useState('');

  useEffect(() => {
    dispatch(getUser({}));
  }, [dispatch]);

  useEffect(() => {
    setUser({ ...profile, password: '' });
    setGender(profile?.outpatientCard?.gender || '');
  }, [profile]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const response = await dispatch(
      updateProfile({
        newData: {
          username: user.username,
          password: user.password,
          active: true,
          email: user.email,
          gender,
          fullName: user.fullName,
        },
      })
    );

    if (response?.type?.includes('fulfilled')) {
      navigate('/');
      showNotification('success', 'Вы успешно изменили профиль', 'Профиль');
    }
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

  const handleRemoveChangePassword = () => {
    setUser({
      ...user,
      password: '',
    });
    setChangePassword(!changePassword);
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
                <input type="text" name="email" className="form-control" id="email" value={user?.outpatientCard?.email || ''} onChange={handleInputChange} />
              </div>
            </div>
          </div>
          <div className="mb-4">
            <br />
            <p>Пол:</p>
            <input type="radio" id="male" name="gender" value="MALE" checked={gender === 'MALE'} onChange={() => setGender('MALE')} className="mx-2" />
            <label htmlFor="male">Мужской</label>
            <input type="radio" id="FEMALE" name="gender" value="FEMALE" checked={gender === 'FEMALE'} onChange={() => setGender('FEMALE')} className="mx-2" />
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
