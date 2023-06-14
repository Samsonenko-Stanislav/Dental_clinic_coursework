import axios from 'axios';
import { loadFromLocalStorage } from './utils/localStorage';
import { logoutUser } from './store/slice/UserSlice';
import { store } from './index';
// baseURL: 'http://localhost:8086',

const axiosApi = axios.create({
  baseURL: 'http://localhost:8086',
});

axiosApi.interceptors.request.use(async (config) => {
  const token = loadFromLocalStorage('token', false);
  if (token) {
    config.headers = {
      Authorization: `Basic ${token}`,
      Accept: 'application/json',
    };
  }
  return config;
});

axiosApi.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    if (error.response.status) {
      return Promise.reject(error);
    }

    store.dispatch(logoutUser());
  }
);

export default axiosApi;
