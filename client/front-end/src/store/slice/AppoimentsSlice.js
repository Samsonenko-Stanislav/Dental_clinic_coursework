import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axiosApi from '../../axiosApi';

export const requestAppointmentsClient = createAsyncThunk('userData/requestAppointmentsClient', async ({ newData, catchFunction }) => {
  return await axiosApi.get('/appointments/clientList', newData);
});

export const requestAppointmentsDoctors = createAsyncThunk('userData/requestAppointmentsDoctors', async ({ newData, catchFunction }) => {
  return await axiosApi.get('/appointments/doctorList');
});

export const editAppointments = createAsyncThunk('userData/editAppointments', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/appointments', newData);
});

export const addAppointments = createAsyncThunk('userData/addAppointments', async ({ newData, catchFunction }) => {
  return await axiosApi.post(`/appointments/add?doctorId=${newData.id}&dateStr=${newData.time}`, newData);
});

export const getAddAppointments = createAsyncThunk('userData/getAddAppointments', async ({ newData, catchFunction }) => {
  return await axiosApi.get(`/appointments/add`);
});

const initialState = {
  loading: false,
  error: null,
  appointmentsDoctor: [],
  appointmentsClient: [],
  doctors: [],
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
    [requestAppointmentsClient.pending]: (state) => {
      state.loading = true;
    },

    [requestAppointmentsClient.fulfilled]: (state, action) => {
      const response = action.payload;

      state.appointmentsClient = response.data;

      state.loading = false;
      state.error = null;
    },

    [requestAppointmentsClient.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },

    [requestAppointmentsDoctors.pending]: (state) => {
      state.loading = true;
    },

    [requestAppointmentsDoctors.fulfilled]: (state, action) => {
      const response = action.payload;

      state.appointmentsDoctor = response.data;
      state.loading = false;
      state.error = null;
    },

    [requestAppointmentsDoctors.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },
    [getAddAppointments.pending]: (state) => {
      state.loading = true;
    },

    [getAddAppointments.fulfilled]: (state, action) => {
      const response = action.payload;
      state.doctors = response.data;
      state.loading = false;
      state.error = null;
    },

    [getAddAppointments.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },
  },
});

export default appointmentsSlice.reducer;
export const { nullifyDataAppointments } = appointmentsSlice.actions;
