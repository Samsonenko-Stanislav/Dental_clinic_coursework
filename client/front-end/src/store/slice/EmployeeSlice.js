import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axiosApi from '../../axiosApi';

export const requestEmployee = createAsyncThunk('userData/requestEmployee', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/employee', newData);
});

export const editEmployee = createAsyncThunk('userData/editEmployee', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/employee', newData);
});

export const addEmployee = createAsyncThunk('userData/addEmployee', async ({ newData, catchFunction }) => {
  return await axiosApi.post('employees', newData);
});

const initialState = {
  loading: false,
  error: null,
  employee: [],
};

const employeeSlice = createSlice({
  name: 'employee',

  initialState,

  reducers: {
    nullifyDataEmployee(state) {
      state = Object.assign(state, initialState);
    },
  },
});

export default employeeSlice.reducer;
export const { nullifyDataEmployee } = employeeSlice.actions;
