import React, { useContext, useState } from 'react';
import Tooth from '../assets/tooth.svg';
import { Link } from 'react-router-dom';
import { UserContext } from '../context/UserContext';
import { useDispatch, useSelector } from 'react-redux';
import { requestLogin } from '../store/slice/UserSlice';
import './Login.css';

function Login() {
  const dispatch = useDispatch();
  const error = useSelector((state) => state.user.error);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { setLoading } = useContext(UserContext);

  const submitHandler = async (e) => {
    e.preventDefault();
    setLoading(true);
    await dispatch(
      requestLogin({
        newData: {
          username,
          password,
        },
      })
    );
    setLoading(false);
  };

  return (
    <div className="text-center container my-2">
      <main className="form-signup w-100 m-auto">
        <form onSubmit={submitHandler}>
          <img className="mb-4" src={Tooth} alt="" width="72" height="57" />
          <br />
          <Link to="/">Вернуться на главную</Link>
          <h1 className="h3 mb-3 fw-normal">Пожалуйста, войдите</h1>

          {error && <div className={'error'}>Неверный логин и/или пароль.</div>}

          <div className="form-floating my-2">
            <input type="text" className="form-control" id="floatingInput" placeholder="Логин" name="username" value={username} onChange={(e) => setUsername(e.target.value)} />
            <label htmlFor="floatingInput">Логин</label>
          </div>
          <div className="form-floating my-2">
            <input
              type="password"
              className="form-control"
              id="floatingPassword"
              placeholder="Пароль"
              name="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />

            <label htmlFor="floatingPassword">Пароль</label>
          </div>

          <button className="w-100 btn btn-lg btn-primary" type="submit">
            Войти
          </button>
        </form>
      </main>
      <Link to="/sign-up">Зарегистрироваться</Link>
    </div>
  );
}

export default Login;
