import React from 'react';
import '../App.css';
import './Login.css';
import 'bootstrap/dist/css/bootstrap.min.css';



function Login() {
        return (
            <div className="text-center">
                    <form action="/login" method="post">
                            <img className="mb-4" src="../tooth.svg" alt="" width="72" height="57" />
                            <br />
                            <a href="/">Вернуться на главную</a>
                            <h1 className="h3 mb-3 fw-normal">Пожалуйста, войдите</h1>

                            <div className={window.location.search.includes('error') ? 'error' : 'hidden'}>
                                    Неверный логин и/или пароль.
                            </div>
                            <div className={window.location.search.includes('logout') ? 'logout' : 'hidden'}>
                                    Вы вышли.
                            </div>

                            <div className="form-floating">
                                    <input type="text" className="form-control" id="floatingInput" placeholder="Логин" name="username" />
                                    <label htmlFor="floatingInput">Логин</label>
                            </div>
                            <div className="form-floating">
                                    <input type="password" className="form-control" id="floatingPassword" placeholder="Пароль" name="password" />
                                    <label htmlFor="floatingPassword">Пароль</label>
                            </div>

                            <button className="w-100 btn btn-lg btn-primary" type="submit">Войти</button>
                    </form>
                    <a href="/sign_up">Зарегистрироваться</a>
            </div>
        );
}

export default Login;

