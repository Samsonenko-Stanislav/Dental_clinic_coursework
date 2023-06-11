import axios from 'axios';

const axiosApi = axios.create({
  baseURL: 'http://localhost:8086',
});

export default axiosApi;
