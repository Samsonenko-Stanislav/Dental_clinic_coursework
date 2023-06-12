import React from 'react';
import ReactDOM from 'react-dom/client';
import { Provider } from 'react-redux';
import App from './App';
import { loadFromLocalStorage } from './utils/localStorage';
import { createReduxStore } from './store/store';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';

const user = loadFromLocalStorage('user');

export const store = createReduxStore({ user });

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </React.StrictMode>
);
