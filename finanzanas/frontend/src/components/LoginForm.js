import React, { useState } from 'react';
import axios from 'axios';

function Login() {
    const [correo, setCorreo] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/auth/login', {
                    correo: correo,
                    password: password
                }, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
            ;
            alert('Login exitoso');
            console.log(response.data); // Aquí puedes guardar el token de autenticación si usas JWT
        } catch (error) {
            alert('Error en el login');
            console.error(error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="email"
                placeholder="Correo"
                value={correo}
                onChange={(e) => setCorreo(e.target.value)}
            />
            <input
                type="password"
                placeholder="Contraseña"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button type="submit">Iniciar sesión</button>
        </form>
    );
}

export default Login;
