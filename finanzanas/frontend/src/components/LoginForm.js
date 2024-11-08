import React, { useState } from 'react';
import axios from 'axios';

// Establecer el token JWT para futuras solicitudes
axios.interceptors.request.use(function (config) {
    const token = localStorage.getItem('token');
    // console.log('Token guardado:', token);
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
    const [error, setError] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/api/auth/login', {
                correo: username,
                password,
            });

            const { id, token } = response.data;

            // Almacenar el token JWT y el id en localStorage
            localStorage.setItem('token', token);
            localStorage.setItem('userId', id);

            // console.log('Token guardado:', token);
            // console.log('ID de usuario guardado:', id);

            window.alert("Se ha iniciado sesión correctamente :)");

            // Redirigir al usuario a la página principal o dashboard
            window.location.href = '/dashboard';
        } catch (error) {
            setError('Credenciales incorrectas');
            console.error(error);
        }
    };

    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh', backgroundColor: '#f5f7fa' }}>
            <form onSubmit={handleSubmit} style={{
                display: 'flex',
                flexDirection: 'column',
                padding: '20px',
                borderRadius: '8px',
                boxShadow: '0px 4px 12px rgba(0, 0, 0, 0.1)',
                backgroundColor: '#ffffff',
                maxWidth: '400px',
                width: '100%'
            }}>
                <h2 style={{ textAlign: 'center', marginBottom: '20px', color: '#333' }}>Iniciar Sesión</h2>

                <div style={{ marginBottom: '15px' }}>
                    <label style={{ marginBottom: '5px', color: '#555' }}>Usuario:</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        style={{
                            width: '100%',
                            padding: '10px',
                            borderRadius: '4px',
                            border: '1px solid #ccc',
                            outline: 'none'
                        }}
                        placeholder="Ingrese su usuario"
                    />
                </div>

                <div style={{ marginBottom: '15px' }}>
                    <label style={{ marginBottom: '5px', color: '#555' }}>Contraseña:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        style={{
                            width: '100%',
                            padding: '10px',
                            borderRadius: '4px',
                            border: '1px solid #ccc',
                            outline: 'none'
                        }}
                        placeholder="Ingrese su contraseña"
                    />
                </div>

                <button
                    type="submit"
                    style={{
                        padding: '10px',
                        borderRadius: '4px',
                        backgroundColor: '#4CAF50',
                        color: 'white',
                        border: 'none',
                        cursor: 'pointer',
                        fontWeight: 'bold',
                        marginTop: '10px'
                    }}
                >
                    Iniciar sesión
                </button>

                {error && <p style={{ color: 'red', textAlign: 'center', marginTop: '10px' }}>{error}</p>}
            </form>
        </div>
    );
}

export default Login;
