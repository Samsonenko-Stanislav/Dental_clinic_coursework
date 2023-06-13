import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axiosApi from '../../axiosApi';
import { saveToLocalStorage } from '../../utils/localStorage';
import { removeEmptyFromObject } from '../../utils/useOnClickOutside';

export const requestLogin = createAsyncThunk('userData/requestLogin', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/login', newData);
});

export const requestRegister = createAsyncThunk('userData/requestRegister', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/sign_up', newData);
});

export const getUser = createAsyncThunk('userData/getUser', async ({ newData, catchFunction }) => {
  return await axiosApi.get('/user/me');
});

export const updateUser = createAsyncThunk('userData/updateUser', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/user/new', removeEmptyFromObject(newData));
});

export const getUsers = createAsyncThunk('userData/getUsers', async ({ newData, catchFunction }) => {
  return await axiosApi.get('/user');
});

export const getSoloUser = createAsyncThunk('userData/getSoloUser', async ({ newData, catchFunction }) => {
  return await axiosApi.get(`/user/${newData.id}`);
});

export const editUser = createAsyncThunk('userData/editUser', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/user/' + newData.id, removeEmptyFromObject(newData));
});

const initialState = {
  loading: false,
  error: null,
  role: [],
  fullName: null,
  token: null,
  username: null,
  email: null,
  gender: null,
  users: [],
  user: {},
};

const userSlice = createSlice({
  name: 'users',

  initialState,

  reducers: {
    logoutUser(state) {
      state = Object.assign(state, initialState);
    },
  },
  extraReducers: {
    [requestLogin.pending]: (state) => {
      state.loading = true;
    },

    [requestLogin.fulfilled]: (state, action) => {
      if (action?.payload) {
        const response = action?.payload?.data;

        saveToLocalStorage('token', response.token, false);
        saveToLocalStorage('role', response?.roles);

        state.token = response.token;
        state.role = response?.roles;
        state.loading = false;
        state.error = null;
      }
    },

    [requestLogin.rejected]: (state) => {
      state.loading = false;
      state.error = 'Неверный логин и/или пароль.';
    },

    [requestRegister.pending]: (state) => {
      state.loading = true;
    },

    [requestRegister.fulfilled]: (state, action) => {
      state.loading = false;
      state.error = null;
    },

    [requestRegister.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },

    [getUser.pending]: (state) => {
      state.loading = true;
    },

    [getUser.fulfilled]: (state, action) => {
      const response = action.payload;
      console.log(response);
      state.loading = false;
      state.error = null;
    },

    [getUser.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },

    [updateUser.pending]: (state) => {
      state.loading = true;
    },

    [updateUser.fulfilled]: (state, action) => {
      const response = action.payload;
      console.log(response);
      state.loading = false;
      state.error = null;
    },

    [updateUser.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },

    [getUsers.pending]: (state) => {
      state.loading = true;
    },

    [getUsers.fulfilled]: (state, action) => {
      state.users = action.payload?.data?.users || [];
      state.loading = false;
      state.error = null;
    },

    [getUsers.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },

    [getSoloUser.pending]: (state) => {
      state.loading = true;
    },
    [getSoloUser.fulfilled]: (state, action) => {
      state.user = action.payload?.data;
      state.loading = false;
      state.error = null;
    },
    [getSoloUser.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },
  },
});

export default userSlice.reducer;
export const { logoutUser } = userSlice.actions;
