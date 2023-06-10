import React from 'react';
import { Link } from "react-router-dom";
import Tooth from '../tooth.svg'

const SignUp = () => {
    return (
      <div className="text-center container my-2">
        <main className="form-signup w-100 m-auto">
          <form >
            <img className="mb-4" src={Tooth} alt="" width="72" height="57" />
            <br />
            <Link to="/">Вернуться на главную</Link>
            <h1 className="h3 mb-3 fw-normal">Пожалуйста, зарегистрируйтесь</h1>
            <div>
              <input type="text" className="form-control" id="floatingName" placeholder="ФИО" name="fullName" required />
              <label htmlFor="floatingName">ФИО</label>
            </div>
            <div>
              <input type="text" className="form-control" id="floatingInput" placeholder="Логин" name="username" required />
              <label htmlFor="floatingInput">Логин</label>
            </div>
            <div>
              <input type="password" className="form-control" id="floatingPassword" placeholder="Пароль" name="password" required />
              <label htmlFor="floatingPassword">Пароль</label>
            </div>
            <div>
              <input type="email" className="form-control" id="EMAIL" placeholder="e-mail" name="email" />
              <label htmlFor="EMAIL">e-mail</label>
            </div>
            <div>
              <input type="radio" id="MALE" name="gender" value="MALE" />
              <label htmlFor="MALE">Мужской</label>
            </div>
            <div>
              <input type="radio" id="FEMALE" name="gender" value="FEMALE" />
              <label htmlFor="FEMALE">Женский</label>
            </div>
            <button className="w-100 btn btn-lg btn-primary" type="submit">Зарегистрироваться</button>
          </form>
          <Link to="/login">Уже есть аккаунт? Войти!</Link>
        </main>
      </div>
    );
  }
export default SignUp;