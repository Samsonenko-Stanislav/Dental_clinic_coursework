import { Navigate } from 'react-router-dom';
import { loadFromLocalStorage } from '../utils/localStorage';

export function RequireAuth({ children, access }) {
  const user = loadFromLocalStorage('user') || null;

  if (!access) {
    return <Navigate to="/" />;
  }

  if (!user) {
    return <Navigate to="/" />;
  }

  return children;
}
