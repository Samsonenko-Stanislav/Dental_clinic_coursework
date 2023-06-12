import { configureStore, getDefaultMiddleware } from '@reduxjs/toolkit';
import { createReducerManager } from './reducerManager';
import userSlice from './slice/UserSlice';
import goodsSlice from './slice/GoodsSlice';
import appointmentsSlice from './slice/AppoimentsSlice';

export function createReduxStore(initialState, asyncReducers) {
  const rootReducers = {
    ...asyncReducers,
    user: userSlice,
    goods: goodsSlice,
    appointments: appointmentsSlice,
  };

  const reducerManager = createReducerManager(rootReducers);

  const middleware = getDefaultMiddleware({
    immutableCheck: false,
    serializableCheck: false,
    thunk: true,
  });

  const store = configureStore({
    reducer: reducerManager.reduce,
    devTools: true,
    preloadedState: initialState,
    middleware,
  });

  store.reducerManager = reducerManager;

  return store;
}
