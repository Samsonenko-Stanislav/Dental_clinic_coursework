import axios from 'axios';
import { loadFromLocalStorage } from './utils/localStorage';
import { logoutUser } from './store/slice/UserSlice';
import { store } from './index';

const axiosApi = axios.create({
  baseURL: 'http://158.160.43.172:8086',
});

axiosApi.interceptors.request.use(async (config) => {
  config.headers = {
    Authorization: `Bearer ${loadFromLocalStorage('token', false)}`,
    Accept: 'application/json',
  };
  return config;
});

axiosApi.interceptors.response.use(
  (response) => {
    return response;
  },
  async () => {
    store.dispatch(logoutUser());
  }
);

export default axiosApi;
