import { createSlice } from "@reduxjs/toolkit";

const promptSlice = createSlice({
  name: "prompt",
  initialState: {
    userSearch: [],
    markdownData: null,
    error: false,
    errorProductNotFound: false
  },
  reducers: {
    setUserPrompt: (state, action) => {
      state.userSearch.push(action.payload);
    },
    getMarkdownData: (state, action) => {
      state.markdownData = action.payload;
    },
    clearUserPrompt: (state) => {
      state.userSearch = {};
    },
    setApiError: (state, action) => {
      state.error = action.payload;
    },
    productNotFound: (state, action) => {
      state.errorProductNotFound = action.payload;
    }
  }
});

export const {
  setUserPrompt,
  clearUserPrompt,
  getMarkdownData,
  setApiError,
  productNotFound
} = promptSlice.actions;

export default promptSlice.reducer;
