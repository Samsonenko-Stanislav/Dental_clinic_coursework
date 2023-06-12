import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  loading: false,
  error: null,
  appointments: [],
};

const appointmentsSlice = createSlice({
  name: 'appointments',

  initialState,

  reducers: {
    nullifyDataGoods(state) {
      state = Object.assign(state, initialState);
    },
  },
});

export default appointmentsSlice.reducer;
export const { nullifyDataGoods } = appointmentsSlice.actions;
