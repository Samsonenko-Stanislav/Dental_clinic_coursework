import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axiosApi from '../../axiosApi';

export const requestAppointments = createAsyncThunk('userData/requestAppointments', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/appointments', newData);
});

export const editAppointments = createAsyncThunk('userData/editAppointments', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/appointments', newData);
});

export const addAppointments = createAsyncThunk('userData/addAppointments', async ({ newData, catchFunction }) => {
  return await axiosApi.post('appointments', newData);
});

const initialState = {
  loading: false,
  error: null,
  appointments: [],
};

const appointmentsSlice = createSlice({
  name: 'appointments',

  initialState,

  reducers: {
    nullifyDataAppointments(state) {
      state = Object.assign(state, initialState);
    },
  },
});

export default appointmentsSlice.reducer;
export const { nullifyDataAppointments } = appointmentsSlice.actions;
