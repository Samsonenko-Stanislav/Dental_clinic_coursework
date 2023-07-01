import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axiosApi from '../../axiosApi';

export const requestEmployee = createAsyncThunk('userData/requestEmployee', async ({ newData, catchFunction }) => {
  return await axiosApi.get('/employee', newData);
});

export const requestSoloEmployee = createAsyncThunk('userData/requestSoloEmployee', async ({ newData, catchFunction }) => {
  return await axiosApi.get('/employee/' + newData.id, newData);
});

export const editEmployee = createAsyncThunk('userData/editEmployee', async ({ newData, catchFunction }) => {
  return await axiosApi.put('/employee/edit/' + newData.id, newData);
});

export const addEmployee = createAsyncThunk('userData/addEmployee', async ({ newData, catchFunction }) => {
  return await axiosApi.post('employee/new', newData);
});

const initialState = {
  loading: false,
  error: null,
  employees: [],
  employee: {},
};

const employeeSlice = createSlice({
  name: 'employee',

  initialState,

  reducers: {
    nullifyDataEmployee(state) {
      state = Object.assign(state, initialState);
    },
  },
  extraReducers: {
    [requestEmployee.pending]: (state) => {
      state.loading = true;
    },

    [requestEmployee.fulfilled]: (state, action) => {
      state.employees = action.payload?.data?.employees;
      state.loading = false;
      state.error = null;
    },

    [requestEmployee.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },
    [requestSoloEmployee.pending]: (state) => {
      state.loading = true;
    },

    [requestSoloEmployee.fulfilled]: (state, action) => {
      state.employee = action.payload?.data?.employee;
      state.loading = false;
      state.error = null;
    },

    [requestSoloEmployee.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },
  },
});

export default employeeSlice.reducer;
export const { nullifyDataEmployee } = employeeSlice.actions;
