import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axiosApi from '../../axiosApi';

export const requestAppointmentsClient = createAsyncThunk('userData/requestAppointmentsClient', async ({ newData, catchFunction }) => {
  return await axiosApi.get('/appointments/clientList', newData);
});

export const requestAppointmentsDoctors = createAsyncThunk('userData/requestAppointmentsDoctors', async ({ newData, catchFunction }) => {
  return await axiosApi.get('/appointments/doctorList');
});

export const addAppointments = createAsyncThunk('userData/addAppointments', async ({ newData, catchFunction }) => {
  return await axiosApi.post(`/appointments/add`, newData);
});

export const getAddAppointments = createAsyncThunk('userData/getAddAppointments', async ({ newData, catchFunction }) => {
  return await axiosApi.get(`/appointments/add`);
});

export const editAppointments = createAsyncThunk('userData/editAppointments', async ({ newData, catchFunction }) => {
  return await axiosApi.put(`/appointments/edit/${newData.appointmentId}`, newData);
});

export const getSoloAppointments = createAsyncThunk('userData/getSoloAppointments', async ({ newData, catchFunction }) => {
  return await axiosApi.get(`/appointments/edit/${newData.id}`, newData);
});

export const cancelAppointment = createAsyncThunk('userData/cancelAppointment', async ({ newData, catchFunction }) => {
  return await axiosApi.delete(`/appointments/cancel/${newData.id}`, newData);
});

const initialState = {
  loading: false,
  error: null,
  appointmentsDoctor: [],
  appointmentsClient: [],
  doctors: [],
  appointment: {},
};

const appointmentsSlice = createSlice({
  name: 'appointments',

  initialState,

  reducers: {
    nullifyDataAppointments(state) {
      state = Object.assign(state, initialState);
    },
    nullifyAppointment(state) {
      state.appointment = {};
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

    [getSoloAppointments.pending]: (state) => {
      state.loading = true;
    },

    [getSoloAppointments.fulfilled]: (state, action) => {
      const response = action.payload;
      state.appointment = response.data;
      state.loading = false;
      state.error = null;
    },

    [getSoloAppointments.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },
  },
});

export default appointmentsSlice.reducer;
export const { nullifyDataAppointments, nullifyAppointment } = appointmentsSlice.actions;
