import { configureStore, getDefaultMiddleware } from '@reduxjs/toolkit';
import { createReducerManager } from './reducerManager';
import userSlice from './UserSlice';

export function createReduxStore(initialState, asyncReducers) {
  const rootReducers = {
    ...asyncReducers,
    user: userSlice,
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
