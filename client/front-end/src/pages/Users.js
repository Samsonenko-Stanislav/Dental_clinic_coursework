import React, { useContext, useEffect, useMemo, useState } from 'react';
import { Link } from 'react-router-dom';
import { UserContext } from '../context/UserContext';
import { useDispatch, useSelector } from 'react-redux';
import { getUsers } from '../store/slice/UserSlice';
import EmptyComponent from '../components/EmptyComonent/EmptyComonent';

const Users = () => {
  const dispatch = useDispatch();
  const usersStore = useSelector((state) => state.user.users) || [];
  const { setLoading } = useContext(UserContext);
  const [active, setActive] = useState(true);

  const users = useMemo(() => {
    return active ? usersStore.filter((user) => user.active) : usersStore.filter((user) => !user.active);
  }, [usersStore, active]);

  useEffect(() => {
    setLoading(true);
    dispatch(getUsers({}));
    setLoading(false);
  }, [dispatch, setLoading]);

  return (
    <>
      <div className="table-responsive">
        {' '}
        {users.length ? (
          <>
            <table className="table table-striped table-sm">
              <thead>
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">Логин</th>
                  <th scope="col">Роли</th>
                  <th scope="col" />
                </tr>
              </thead>
              <tbody>
                {users.map((user) => (
                  <tr key={user.id}>
                    <td>{user.id}</td>
                    <td className={user.active ? '' : 'archived'}>{user.username}</td>
                    <td>
                      {user.roles.map((role, index) => (
                        <p key={index}>{role},</p>
                      ))}
                    </td>
                    <td>
                      <Link to={`/user/edit/${user.id}`}>Редактировать</Link>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>{' '}
          </>
        ) : (
          <EmptyComponent />
        )}{' '}
        <div className="my-4">
          <button className="btn btn-primary mx-2" onClick={() => setActive(false)}>
            Показать архивных пользователей
          </button>
          <button className="btn btn-primary mx-2" onClick={() => setActive(true)}>
            Скрыть архивных пользователей
          </button>
          <Link to="/user/new" className="btn btn-primary mx-2">
            Создать нового
          </Link>
        </div>
      </div>
    </>
  );
};

export default Users;
