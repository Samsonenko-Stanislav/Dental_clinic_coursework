import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axiosApi from '../../axiosApi';

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
  return await axiosApi.post('/user/me');
});

export const getUsers = createAsyncThunk('userData/getUsers', async ({ newData, catchFunction }) => {
  return await axiosApi.get('/user/me');
});

const initialState = {
  loading: false,
  error: null,
  role: null,
  fullName: null,
  token: null,
  username: null,
  email: null,
  gender: null,
  users: [],
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
      const response = action.payload;
      console.log(response);
      state.loading = false;
      state.error = null;
    },

    [requestLogin.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },

    [requestRegister.pending]: (state) => {
      state.loading = true;
    },

    [requestRegister.fulfilled]: (state, action) => {
      const response = action.payload;
      console.log(response);
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
      const response = [] || action.payload;
      state.users = response;
      state.loading = false;
      state.error = null;
    },

    [getUsers.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },
  },
});

export default userSlice.reducer;
export const { logoutUser } = userSlice.actions;
