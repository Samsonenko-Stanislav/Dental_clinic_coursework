import React from 'react';
import '../App.css';
import './Login.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios';
import Tooth from '../tooth.svg';
import { Link } from 'react-router-dom';


function Login() {

  const submitHandler = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.get('http://localhost:8086/price');
      console.log(response);
    } catch (e) {
      console.log(e);
    }
  };


  return (
    <div className='text-center container my-2'>
      <form onSubmit={submitHandler}>
        <img className='mb-4' src={Tooth} alt='' width='72' height='57' />
        <br />
        <Link to='/'>Вернуться на главную</Link>
        <h1 className='h3 mb-3 fw-normal'>Пожалуйста, войдите</h1>

        <div className={window.location.search.includes('error') ? 'error' : 'hidden'}>
          Неверный логин и/или пароль.
        </div>
        <div className={window.location.search.includes('logout') ? 'logout' : 'hidden'}>
          Вы вышли.
        </div>

        <div className='form-floating my-2'>
          <input type='text' className='form-control' id='floatingInput' placeholder='Логин' name='username' />
          <label htmlFor='floatingInput'>Логин</label>
        </div>
        <div className='form-floating my-2'>
          <input type='password' className='form-control' id='floatingPassword' placeholder='Пароль' name='password' />
          <label htmlFor='floatingPassword'>Пароль</label>
        </div>

        <button className='w-100 btn btn-lg btn-primary' type='submit'>Войти</button>
      </form>
      <Link to='/sign-up'>Зарегистрироваться</Link>
    </div>
  );
}

export default Login;