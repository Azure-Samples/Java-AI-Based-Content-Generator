import { createSlice } from "@reduxjs/toolkit";

const userSlice = createSlice({
  name: "user",
  initialState: {
    userData: ""
  },
  reducers: {
    setUserName: (state, action) => {
      state.userData = action.payload;
    },
    clearUserName: (state) => {
      state.userData = null;
    }
  }
});

export const { setUserName, clearUserName } = userSlice.actions;

export default userSlice.reducer;
