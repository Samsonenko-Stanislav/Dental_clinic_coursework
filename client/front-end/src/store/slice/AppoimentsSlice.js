import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axiosApi from '../../axiosApi';

export const requestAppointments = createAsyncThunk('userData/requestAppointments', async ({ newData, catchFunction }) => {
  return await axiosApi.get('/appointments', newData);
});

export const requestAppointmentsDoctors = createAsyncThunk('userData/requestAppointmentsDoctors', async ({ newData, catchFunction }) => {
  return await axiosApi.get('/appointments', newData);
});

export const editAppointments = createAsyncThunk('userData/editAppointments', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/appointments', newData);
});

export const addAppointments = createAsyncThunk('userData/addAppointments', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/appointments/new', newData);
});

const initialState = {
  loading: false,
  error: null,
  appointmentsDoctor: [],
  appointmentsClient: [],
};

const appointmentsSlice = createSlice({
  name: 'appointments',

  initialState,

  reducers: {
    nullifyDataAppointments(state) {
      state = Object.assign(state, initialState);
    },
  },
  extraReducers: {
    [requestAppointments.pending]: (state) => {
      state.loading = true;
    },

    [requestAppointments.fulfilled]: (state, action) => {
      const response = action.payload;
      console.log(response);
      state.loading = false;
      state.error = null;
    },

    [requestAppointments.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },

    [requestAppointmentsDoctors.pending]: (state) => {
      state.loading = true;
    },

    [requestAppointmentsDoctors.fulfilled]: (state, action) => {
      const response = action.payload;

      console.log( response.data.appointmentsDoctor, response.data.appointmentsClient);
      state.appointmentsDoctor = response.data.appointmentsDoctor;
      state.appointmentsClient = response.data.appointmentsClient;
      state.loading = false;
      state.error = null;
    },

    [requestAppointmentsDoctors.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },
  },
});

export default appointmentsSlice.reducer;
export const { nullifyDataAppointments } = appointmentsSlice.actions;
