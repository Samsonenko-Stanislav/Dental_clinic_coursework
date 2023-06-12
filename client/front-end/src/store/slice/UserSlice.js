import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axiosApi from '../../axiosApi';

export const requestLogin = createAsyncThunk('userData/requestLogin', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/login', newData);
});

export const requestRegister = createAsyncThunk('userData/requestRegister', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/sign_up', newData);
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
  },
});

export default userSlice.reducer;
export const { logoutUser } = userSlice.actions;
