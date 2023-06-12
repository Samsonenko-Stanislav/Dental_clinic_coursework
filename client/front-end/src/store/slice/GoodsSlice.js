import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axiosApi from '../../axiosApi';
import { requestPrice } from './PriceSlice';

export const requestGoods = createAsyncThunk('userData/requestGoods', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/goods', newData);
});

export const editGoods = createAsyncThunk('userData/editGoods', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/goods', newData);
});

export const getGood = createAsyncThunk('userData/editGoods', async ({ newData, catchFunction }) => {
  return await axiosApi.get('/good', newData);
});

export const addGoods = createAsyncThunk('userData/addGoods', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/goods', newData);
});

const initialState = {
  loading: false,
  error: null,
  goods: [],
  good: {},
};

const goodsSlice = createSlice({
  name: 'goods',

  initialState,

  reducers: {
    nullifyDataGoods(state) {
      state = Object.assign(state, initialState);
    },
  },
  extraReducers: {
    [getGood.pending]: (state) => {
      state.loading = true;
    },

    [getGood.fulfilled]: (state, action) => {
      state.prices = action.payload.data.goods;
      state.loading = false;
      state.error = null;
    },

    [getGood.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },
  },
});

export default goodsSlice.reducer;
export const { nullifyDataGoods } = goodsSlice.actions;
