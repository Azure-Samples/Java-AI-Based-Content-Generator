import axios from "axios";
import {msalInstance} from "../index";

const instance = axios.create({
  baseURL: process.env.REACT_APP_SERVICE_BASE_URL,
  timeout: 10000
});

// Request Interceptor
instance.interceptors.request.use(function (config) {
  config.headers['api-key'] = process.env.REACT_APP_SERVICE_ACCESS_KEY;
  return config;
}, function (error) {
  return Promise.reject(error);
});

// Response Interceptor
instance.interceptors.response.use(
    (response) => {
      return response;
    },
    (error) => {
      if (error.response) {
        const { status } = error.response;

        if (status === 401) {
          msalInstance.clearCache();
          window.location.href = window.location.origin;
        }
      } else if (error.request) {
        console.error('No response received:', error.request);
      } else {
        console.error('Error:', error.message);
      }
      return Promise.reject(error);
    }
);

export default instance;
