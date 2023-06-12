import { Navigate } from 'react-router-dom';
import { loadFromLocalStorage } from '../utils/localStorage';
import { useSelector } from 'react-redux';

export function RequireAuth({ children, access }) {
  const user = useSelector((state) => state.user.token) || loadFromLocalStorage('user');

  if (!access) {
    return <Navigate to="/" />;
  }

  if (!user) {
    return <Navigate to="/" />;
  }

  return children;
}
