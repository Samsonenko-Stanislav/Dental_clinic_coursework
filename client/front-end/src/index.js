import React from 'react';
import ReactDOM from 'react-dom/client';
import { Provider } from 'react-redux';
import App from './App';
import { loadFromLocalStorage } from './utils/localStorage';
import { createReduxStore } from './store/store';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';

const token = loadFromLocalStorage('token', false) || null;
const role = loadFromLocalStorage('role') || [];
const user = { token, role };

export const store = createReduxStore({ user });

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <Provider store={store} >
    <App />
  </Provider>
);
