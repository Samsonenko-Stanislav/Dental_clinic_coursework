import axios from 'axios';

const axiosApi = axios.create({
  baseURL: 'http://158.160.43.172:8086',
});

export default axiosApi;
