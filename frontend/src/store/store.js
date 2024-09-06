// store.js
import { configureStore } from "@reduxjs/toolkit";
import userReducer from "./userSlice";
import promptReducer from "./promptSlice";

export const store = configureStore({
  reducer: {
    user: userReducer,
    prompt: promptReducer
  }
});
