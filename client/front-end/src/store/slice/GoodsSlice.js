import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axiosApi from '../../axiosApi';

export const requestGoods = createAsyncThunk('userData/requestGoods', async ({ newData, catchFunction }) => {
  return await axiosApi.get('/good', newData);
});

export const editGoods = createAsyncThunk('userData/editGoods', async ({ newData, catchFunction }) => {
  return await axiosApi.put(`/good/${newData.id}`, newData);
});

export const getGood = createAsyncThunk('userData/getGood', async ({ newData, catchFunction }) => {
  return await axiosApi.get('/good/' + newData.id, newData);
});

export const addGoods = createAsyncThunk('userData/addGoods', async ({ newData, catchFunction }) => {
  return await axiosApi.post('/good/new', newData);
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
    [requestGoods.pending]: (state) => {
      state.loading = true;
    },

    [requestGoods.fulfilled]: (state, action) => {
      state.goods = action.payload.data.goods;
      state.loading = false;
      state.error = null;
    },

    [requestGoods.rejected]: (state, action) => {
      state.loading = false;
      state.error = action.error.message;
    },

    [getGood.pending]: (state) => {
      state.loading = true;
    },

    [getGood.fulfilled]: (state, action) => {
      state.good = action.payload.data.good;
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
