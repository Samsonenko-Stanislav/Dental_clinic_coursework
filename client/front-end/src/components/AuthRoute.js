import { Navigate } from 'react-router-dom';
import { loadFromLocalStorage } from '../utils/localStorage';
import { useSelector } from 'react-redux';
import AccessDenied from "./AccessDeniedComponent/AccessDenied";

export function RequireAuth({ children, access }) {
  const user = useSelector((state) => state.user.token) || loadFromLocalStorage('user');

  if (!user) {
    return <Navigate to="/login" />;
  }

  if (!access) {
    return <AccessDenied/>;
  }

  return children;
}
