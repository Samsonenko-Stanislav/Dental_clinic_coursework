import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axiosApi from '../../axiosApi';

export const requestAppointmentsDoctors = createAsyncThunk('userData/requestAppointmentsDoctors', async ({ newData, catchFunction }) => {
  return await axiosApi.get('/appointments');
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
    [requestAppointmentsDoctors.pending]: (state) => {
      state.loading = true;
    },

    [requestAppointmentsDoctors.fulfilled]: (state, action) => {
      const response = action.payload;

      console.log(response.data.appointmentsDoctor, response.data.appointmentsClient);
      state.appointmentsDoctor = response.data.appointmentsDoctor;
      state.appointmentsClient = response.data.appointmentsClient;
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
