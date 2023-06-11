import React, { useState } from 'react';
import '../App.css';
import './Login.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function Login() {
        const [authStatus, setAuthStatus] = useState('');
        const handleSubmit = async (e) => {
                e.preventDefault();
                const { username, password } = e.target.elements;
                try {
                        const response = await axios.get('http://localhost:8086/price', {
                                username: username.value,
                                password: password.value,
                        });
                        if (response.data.authorized) {
                                setAuthStatus('Вы авторизованы');
                                toast.success('Аутентификация прошла успешно!');
                        } else {
                                setAuthStatus('Вы не авторизованы');
                                toast.warn('Аутентификация не удалась');
                        }
                } catch (error) {
                        setAuthStatus('Неправильный логин и/или пароль');
                        toast.error('Аутентификация не удалась');
                }
        };

        return (
            <div className="log">
                    <div className="text-center">
                            <form onSubmit={handleSubmit} className="form">
                                    <img className="mb-4" src="/tooth.svg" alt="img" width="72" height="57" />
                                    <br />
                                    <a href="/">Вернуться на главную</a>
                                    <h1 className="h3 mb-3 fw-normal">Пожалуйста, войдите</h1>

                                    <div className="auth-status">{authStatus ? authStatus : 'Введите данные для авторизации'}</div>

                                    <div className="form-floating">
                                            <input type="text" className="form-control" id="floatingInput" placeholder="Логин" name="username" />
                                            <label htmlFor="floatingInput">Логин</label>
                                    </div>
                                    <div className="form-floating">
                                            <input type="password" className="form-control" id="floatingPassword" placeholder="Пароль" name="password" />
                                            <label htmlFor="floatingPassword">Пароль</label>
                                    </div>

                                    <button className="w-100 btn btn-lg btn-primary mt-1" type="submit">
                                            Войти
                                    </button>
                            </form>
                            <a>Нет аккаунта?</a>
                            <a href="/sign_up"> Зарегистрироваться</a>
                    </div>
            </div>
        );
}

export default Login;
