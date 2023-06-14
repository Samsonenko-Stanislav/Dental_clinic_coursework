import React, { useContext, useEffect, useMemo, useState } from 'react';
import { Link } from 'react-router-dom';
import { UserContext } from '../context/UserContext';
import { useDispatch, useSelector } from 'react-redux';
import { getUsers } from '../store/slice/UserSlice';
import { Table } from 'antd';

const Users = () => {
  const dispatch = useDispatch();
  const usersStore = useSelector((state) => state.user.users);
  const { setLoading } = useContext(UserContext);
  const [active, setActive] = useState(true);

  const users = useMemo(() => {
    if (!usersStore) return [];
    else return active ? usersStore.filter((user) => user.active) : usersStore.filter((user) => !user.active);
  }, [active, usersStore]);

  useEffect(() => {
    setLoading(true);
    dispatch(getUsers({}));
    setLoading(false);
  }, [dispatch, setLoading]);

  const columns = [
    {
      title: '#',
      dataIndex: 'id',
      key: 'id',
    },
    {
      title: 'Логин',
      dataIndex: 'username',
      key: 'username',
      render: (text, record) => <td className={record.active ? '' : 'archived'}>{record.username}</td>,
    },

    {
      title: 'Роли',
      dataIndex: 'roles',
      key: 'roles',
      render: (text, record) => <div>{record.roles.join(', ')}</div>,
    },
    {
      title: '',
      dataIndex: 'address',
      key: 'address',
      render: (text, record) => <Link to={`/user/edit/${record.id}`}>Редактировать</Link>,
    },
  ];

  return (
    <>
      <div className="table-responsive">
        {users.length ? (
          <div className="my-4 list">
            <Table dataSource={users} pagination={false} columns={columns} />
          </div>
        ) : null}
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
