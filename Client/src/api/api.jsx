import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

api.interceptors.request.use((config) => {
  const auth = localStorage.getItem("auth");
  if (auth) {
    const { email, password } = JSON.parse(auth);
    const credentials = btoa(`${email}:${password}`);
    config.headers.Authorization = `Basic ${credentials}`;
  }
  return config;
}, (error) => Promise.reject(error));

export default api;