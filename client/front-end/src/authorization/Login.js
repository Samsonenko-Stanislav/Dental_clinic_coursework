import React, {useState} from 'react';
import '../App.css';
import './Login.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Tooth from '../tooth.svg';
import {Link} from 'react-router-dom';
import axiosApi from "../axiosApi";


function Login() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')

  const submitHandler = async (e) => {
    e.preventDefault();
    try {
      const response = await axiosApi.post('/login' ,{username:email, password});
      console.log(response);
    } catch (e) {
      console.log(e);
    }
  };


  return (
    <div className='text-center container my-2'>
      <main className='form-signup w-100 m-auto'>

        <form onSubmit={submitHandler}>
          <img className='mb-4' src={Tooth} alt='' width='72' height='57'/>
          <br/>
          <Link to='/'>Вернуться на главную</Link>
          <h1 className='h3 mb-3 fw-normal'>Пожалуйста, войдите</h1>

          <div className={window.location.search.includes('error') ? 'error' : 'hidden'}>
            Неверный логин и/или пароль.
          </div>
          <div className={window.location.search.includes('logout') ? 'logout' : 'hidden'}>
            Вы вышли.
          </div>

          <div className='form-floating my-2'>
            <input type='text' className='form-control' id='floatingInput' placeholder='Логин' name='username'
                   value={email} onChange={e => setEmail(e.target.value)}/>
            <label htmlFor='floatingInput'>Логин</label>
          </div>
          <div className='form-floating my-2'>
            <input type='password' className='form-control' id='floatingPassword' placeholder='Пароль'
                   name='password' value={password} onChange={e => setPassword(e.target.value)}/>

            <label htmlFor='floatingPassword'>Пароль</label>
          </div>

          <button className='w-100 btn btn-lg btn-primary' type='submit'>Войти</button>
        </form>
      </main>
      <Link to='/sign-up'>Зарегистрироваться</Link>
    </div>
  );
}

export default Login;