// src/AxiosConfig.js
import axios from 'axios';

// Establecer el token JWT para futuras solicitudes
axios.interceptors.request.use(
    function (config) {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    function (error) {
        return Promise.reject(error);
    }
);

export default axios;
