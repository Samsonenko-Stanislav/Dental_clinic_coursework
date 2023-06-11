import React, { useState } from 'react';
import '../App.css';
import './Login.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios';
import { toast } from 'react-toastify';

function Registration() {
    const [authStatus, setAuthStatus] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        const { fullName, username, password, email, gender } = e.target.elements;
        try {
            const response = await axios.post('http://localhost:8086/register', {
                fullName: fullName.value,
                username: username.value,
                password: password.value,
                email: email.value ? email.value : '', //делаем email опциональным
                gender: gender.value,
            });
            setAuthStatus('Вы успешно зарегистрировались');
            toast.success('Регистрация прошла успешно!');
        } catch (error) {
            setAuthStatus('Не удалось зарегистрироваться');
            toast.error('Регистрация не удалась');
        }
    };

    return (
        <div className="log">
            <div className="text-center">
                <form onSubmit={handleSubmit} className="form">
                    <img className="mb-4" src="/tooth.svg" alt="img" width="72" height="57" />
                    <br />
                    <a href="/">Вернуться на главную</a>
                    <h1 className="h3 mb-3 fw-normal">Зарегистрируйтесь</h1>

                    <div className="auth-status">{authStatus ? authStatus : 'Введите данные для регистрации'}</div>

                    <div className="form-floating">
                        <input type="text" className="form-control" id="floatingFullName" placeholder="ФИО" name="fullName" />
                        <label htmlFor="floatingFullName">ФИО</label>
                    </div>
                    <div className="form-floating">
                        <input type="text" className="form-control" id="floatingUsername" placeholder="Логин" name="username" />
                        <label htmlFor="floatingUsername">Логин</label>
                    </div>
                    <div className="form-floating">
                        <input type="email" className="form-control" id="floatingEmail" placeholder="Email" name="email" />
                        <label htmlFor="floatingEmail">Email</label>
                    </div>
                    <div className="form-floating">
                        <input type="password" className="form-control" id="floatingPassword" placeholder="Пароль" name="password" />
                        <label htmlFor="floatingPassword">Пароль</label>
                    </div>
                    <div className="form-floating">
                        <select id="floatingGender" className="form-control" placeholder="Пол" name="gender">
                            <option value="">Выберите пол</option>
                            <option value="MALE">Мужской</option>
                            <option value="FEMALE">Женский</option>
                        </select>
                        <label htmlFor="floatingGender">Пол</label>
                    </div>

                    <button className="w-100 btn btn-lg btn-primary mt-1" type="submit">
                        Зарегистрироваться
                    </button>
                </form>
                <a>Уже зарегистрированы?</a>
                <a href="/sign_in"> Войти?</a>
            </div>
        </div>
    );
}

export default Registration;
