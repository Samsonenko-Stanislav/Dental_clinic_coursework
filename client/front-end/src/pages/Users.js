import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

const Users = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const response = await fetch('https://jsonplaceholder.typicode.com/users');
      const data = await response.json();
      setUsers(data.map(user=>({...user, roles:'Admin'})));
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  return (
    <>

      <div className='table-responsive'>
        <table className='table table-striped table-sm'>
          <thead>
          <tr>
            <th scope='col'>#</th>
            <th scope='col'>Логин</th>
            <th scope='col'>Роли</th>
            <th scope='col' />
          </tr>
          </thead>
          <tbody>
          {users.length ? users.map((user) => (
            <tr key={user.id}>
              <td>{user.id}</td>
              <td className={user.active ? '' : 'archived'}>{user.username}</td>
              <td>

                {user.roles}
                {/*{user.roles.map((role, index) => (*/}
                {/*  <p key={index}>{role},</p>*/}
                {/*))}*/}
              </td>
              <td>
                <Link to={`/user/${user.id}`}>Редактировать</Link>
              </td>
            </tr>
          )) : null}
          </tbody>
        </table>
      </div>

      <Link to='/user/new' className='btn btn-primary mx-2'>Создать нового</Link>
      <button className='btn btn-primary mx-2'>Показать архивных пользователей</button>
      <button className='btn btn-primary mx-2'>Скрыть архивных пользователей</button>
    </>
  );
};

export default Users;