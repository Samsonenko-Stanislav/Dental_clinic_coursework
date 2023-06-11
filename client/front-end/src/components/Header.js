import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import { UserContext } from '../UserContext';

function Header({ userFullName = '' }) {
  const { user, role } = useContext(UserContext);

  return (
    <header className='p-3 bg-dark text-white'>
      <div className='container'>
        <div className='d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start'>
          <Link to='/' className='d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none'>
            <svg className='bi me-2' width='40' height='32' role='img' aria-label='Bootstrap'>
              <use xlinkHref='#bootstrap' />
            </svg>
          </Link>

          <ul className='nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0'>
            <li>
              <Link to='/' className='nav-link px-2 text-secondary'>Домой</Link>
            </li>
            {user && <>
              {role !== 'admin' && <li>
                <Link to='/appointments' className='nav-link px-2 text-white'>Список записей</Link>
              </li>}

              {role === 'admin' && <>
                <li>
                  <Link to='/user' className='nav-link px-2 text-white'>Пользователи</Link>
                </li>
                <li>
                  <Link to='/employee' className='nav-link px-2 text-white'>Сотрудники</Link>
                </li>
                <li>
                  <Link to='/good' className='nav-link px-2 text-white'>Услуги</Link>
                </li>
              </>}
            </>
            }

            {role !== 'admin' && <li>
              <Link to='/price' className='nav-link px-2 text-white'>Прейскурант</Link>
            </li>}
          </ul>

          {!user ? (
            <div className='text-end'>
              <Link to='/login' className='btn btn-outline-light me-2'>Войти</Link>
              <Link to='/sign-up' className='btn btn-warning'>Зарегистироваться</Link>
            </div>
          ) : (
            <div className='dropdown text-end'>

              <button className='btn dropdown-toggle' type={'button'} data-bs-toggle='dropdown' aria-expanded='false'>
                <img
                  src='https://e7.pngegg.com/pngimages/545/892/png-clipart-computer-icons-user-profile-web-user-icon-face-monochrome.png'
                  alt='mdo' width='32' height='32' className='rounded-circle' />
              </button>

              <ul className='dropdown-menu text-small'>
                <li>
                  <div className='dropdown-item'>
                    <span>Добрый день, </span>
                    <span>{userFullName}</span>
                  </div>
                </li>
                <li>
                  <hr className='dropdown-divider' />
                </li>
                <li>
                  <Link to='/user/me' className='dropdown-item'>Профиль</Link>
                </li>
                <li>
                  <hr className='dropdown-divider' />
                </li>
                <li>
                  <Link to='/logout' className='dropdown-item'>Выйти</Link>
                </li>
              </ul>
            </div>
          )}
        </div>
      </div>
    </header>
  );
}

export default Header;