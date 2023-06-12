import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axiosApi from "../../axiosApi";

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
  employees: [],
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
      const response = action.payload;
      console.log(response);
      state.loading = false;
      state.error = null;
    },

    [requestEmployee.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },
  },
});

export default employeeSlice.reducer;
export const { nullifyDataEmployee } = employeeSlice.actions;
