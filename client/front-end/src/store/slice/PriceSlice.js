import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axiosApi from '../../axiosApi';

const initialState = {
  loading: false,
  error: null,
  prices: [],
};

export const requestPrice = createAsyncThunk('userData/requestPrice', async ({ newData, catchFunction }) => {
  return await axiosApi.get('/price', newData);
});

const priceSlice = createSlice({
  name: 'price',

  initialState,

  reducers: {
    nullifyDataPrice(state) {
      state = Object.assign(state, initialState);
    },
  },
  extraReducers: {
    [requestPrice.pending]: (state) => {
      state.loading = true;
    },

    [requestPrice.fulfilled]: (state, action) => {
      state.prices = action.payload.data.goods;
      state.loading = false;
      state.error = null;
    },

    [requestPrice.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },
  },
});

export default priceSlice.reducer;
export const { nullifyDataGoods } = priceSlice.actions;
