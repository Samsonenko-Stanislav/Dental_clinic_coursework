import { createSlice } from '@reduxjs/toolkit';

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
