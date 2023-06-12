import { Navigate } from 'react-router-dom';
import { loadFromLocalStorage } from '../utils/localStorage';
import { useSelector } from 'react-redux';

export function NoLoginRoute({ children }) {
  const user = useSelector((state) => state.user.token) || loadFromLocalStorage('user');

  if (user) {
    return <Navigate to="/" />;
  }

  return children;
}
