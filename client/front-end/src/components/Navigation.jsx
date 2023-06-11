import React from 'react';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

const Navigation = ({ role }) => (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
        <div className="container-fluid">
            <button
                className="navbar-toggler"
                type="button"
                data-toggle="collapse"
                data-target="#navbarNav"
                aria-controls="navbarNav"
                aria-expanded="false"
                aria-label="Toggle navigation"
            >
                <span className="navbar-toggler-icon">
                    <i className="fa fa-caret-down"></i>
                </span>
            </button>
            <div className="collapse navbar-collapse" id="navbarNav">
                <ul className="navbar-nav mr-auto">
                    <li className="nav-item">
                        <Link className="nav-link" to="/">
                            Домой
                        </Link>
                    </li>
                    {role === 'USER' && (
                        <>
                            <li className="nav-item">
                                <Link className="nav-link" to="/price">
                                    Прейскурант
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/appointments">
                                    Записаться на прием
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/health-record">
                                    Электронная карта
                                </Link>
                            </li>
                        </>
                    )}
                    {role === 'ADMIN' && (
                        <>
                            <li className="nav-item">
                                <Link className="nav-link" to="/users">
                                    Пользователи
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/employees">
                                    Сотрудники
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/services">
                                    Платные медицинские услуги
                                </Link>
                            </li>
                        </>
                    )}
                    {role === 'DOCTOR' && (
                        <>
                            <li className="nav-item">
                                <Link className="nav-link" to="/patients">
                                    Просмотр списка записанных пациентов
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/medical-card">
                                    Работа с медицинской картой пациента
                                </Link>
                            </li>
                        </>
                    )}
                </ul>
                <ul className="navbar-nav ml-auto profile">
                    {(role === 'USER' || role === 'ADMIN' || role === 'DOCTOR') && (
                        <li className="nav-item ml-3 d-flex align-items-center">
                            <img src="/profile.png" alt="Profile" height="25px" width="25px"/>
                        </li>

                    )}
                    {role === 'incognito' && (
                        <a className="nav-item">
                            <Link className="nav-link" to="/sign_up">
                                Войти
                            </Link>
                        </a>
                    )}
                </ul>
            </div>
        </div>
    </nav>
);

export default Navigation;
