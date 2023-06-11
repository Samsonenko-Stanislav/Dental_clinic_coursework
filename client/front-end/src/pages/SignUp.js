import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import Tooth from '../tooth.svg';
import axiosApi from '../axiosApi';

const SignUp = () => {
  const [fullName, setFullName] = useState('');
  const [username, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState('');
  const [gender, setGender] = useState('');

  const submitHandler = async (e) => {
    e.preventDefault();
    try {
      const response = await axiosApi.post('/sign_up', { fullName, username, password, email, gender });

      console.log(response.data);
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <div className="text-center">
      <main className="form-signup w-100 m-auto">
        <form onSubmit={submitHandler}>
          <img className="mb-4" src={Tooth} alt="" width="72" height="57" />
          <br />
          <Link to="/">Вернуться на главную</Link>
          <h1 className="h3 mb-3 fw-normal">Пожалуйста, зарегистрируйтесь</h1>
          <div>
            {' '}
            <label htmlFor="floatingName">ФИО</label>
            <input
              value={fullName}
              onChange={(e) => setFullName(e.target.value)}
              type="text"
              className="form-control"
              id="floatingName"
              placeholder="ФИО"
              name="fullName"
              required
            />
          </div>
          <div>
            {' '}
            <label htmlFor="floatingInput">Логин</label>
            <input
              value={username}
              onChange={(e) => setUserName(e.target.value)}
              type="text"
              className="form-control"
              id="floatingInput"
              placeholder="Логин"
              name="username"
              required
            />
          </div>
          <div>
            {' '}
            <label htmlFor="floatingPassword">Пароль</label>
            <input
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              type="password"
              className="form-control"
              id="floatingPassword"
              placeholder="Пароль"
              name="password"
              required
            />
          </div>
          <div>
            {' '}
            <label htmlFor="EMAIL">e-mail</label>
            <input value={email} onChange={(e) => setEmail(e.target.value)} type="email" className="form-control" id="EMAIL" placeholder="e-mail" name="email" />
          </div>
          <div>
            <input type="radio" id="MALE" name="gender" value={gender} onChange={(e) => setGender('MALE')} />
            <label htmlFor="MALE">Мужской</label>
          </div>
          <div>
            <input value={gender} onChange={(e) => setGender('FEMALE')} type="radio" id="FEMALE" name="gender" />
            <label htmlFor="FEMALE">Женский</label>
          </div>
          <button className="w-100 btn btn-lg btn-primary" type="submit">
            Зарегистрироваться
          </button>
        </form>
        <Link to="/login">Уже есть аккаунт? Войти!</Link>
      </main>
    </div>
  );
};
export default SignUp;
