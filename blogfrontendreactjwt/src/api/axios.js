import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
    },
    timeout: 5000,
});

api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('jwtToken');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        config.headers['Cache-Control'] = 'no-cache, no-store, must-revalidate';
        return config;
    },
    (error) => Promise.reject(error)
);

api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response) {
            console.error('HTTP Error:', error.response.status, error.response.data);
            if (error.response.status === 403) {
                console.warn('Redirigiendo al Login debido a error 403');
                const message = encodeURIComponent("Acceso denegado. Inicia sesión nuevamente.");
                window.location.href = `/login?error=${message}`;
            }
        } else if (error.request) {
            console.error('No response received:', error.request);
        } else {
            console.error('Request Error:', error.message);
        }
        return Promise.reject(error);
    }
);

export default api;
