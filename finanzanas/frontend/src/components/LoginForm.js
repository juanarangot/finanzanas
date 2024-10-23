import React, { useState } from 'react';
import axios from 'axios';


// Establecer el token JWT para futuras solicitudes
axios.interceptors.request.use(function (config) {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, function (error) {
    return Promise.reject(error);
});

function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [token2, setToken] = useState('');
    const [error, setError] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/api/auth/login', {
                correo: username,
                password,
            });

            console.log("datos", response.data);
            // Aquí se asume que el token viene en la respuesta como 'response.data.token'
            const token = response.data.token;

            // const token = response.data.jwt;
            // Almacenar el token JWT en localStorage

            localStorage.setItem('token', response.data.token);
            console.log('Token guardado:', token2);

            window.alert("Se ha iniciado sesión correctamente :)");


            // Redirigir al usuario a la página principal o dashboard
            window.location.href = '/home';
        } catch (error) {
            setError('Credenciales incorrectas');
            console.error(error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Usuario:</label>
                <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
            </div>
            <div>
                <label>Contraseña:</label>
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
            </div>
            <button type="submit">Iniciar sesión</button>
            {error && <p>{error}</p>}
        </form>
    );
}

export default Login;
