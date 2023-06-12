import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axiosApi from '../../axiosApi';

export const requestGoods = createAsyncThunk('userData/requestGoods', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/goods', newData);
});

export const editGoods = createAsyncThunk('userData/editGoods', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/goods', newData);
});

export const addGoods = createAsyncThunk('userData/addGoods', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/goods', newData);
});

const initialState = {
  loading: false,
  error: null,
  goods: [],
};

const goodsSlice = createSlice({
  name: 'goods',

  initialState,

  reducers: {
    nullifyDataGoods(state) {
      state = Object.assign(state, initialState);
    },
  },
});

export default goodsSlice.reducer;
export const { nullifyDataGoods } = goodsSlice.actions;
