import React, { useState } from 'react';
import axios from 'axios';

function Register() {
    const [nombre, setNombre] = useState('');
    const [correo, setCorreo] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/auth/registrar', {
                nombre,
                correo,
                password
            });

            const { id, token } = response.data;

            localStorage.setItem('token', token);
            localStorage.setItem('userId', id);

            console.log('Token guardado:', response.data.token);
            console.log('ID de usuario guardado:', response.data.idUsuario);

            alert('Registro exitoso');
            // console.log(response.data);


            setNombre('');
            setCorreo('');
            setPassword('');
            setError(null);

            // Redirigir al usuario a la página principal o dashboard
            window.location.href = '/dashboard';
        } catch (error) {
            setError('Error al registrar');
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
                <h2 style={{ textAlign: 'center', marginBottom: '20px', color: '#333' }}>Registro</h2>

                <div style={{ marginBottom: '15px' }}>
                    <label style={{ marginBottom: '5px', color: '#555' }}>Nombre:</label>
                    <input
                        type="text"
                        placeholder="Nombre"
                        value={nombre}
                        onChange={(e) => setNombre(e.target.value)}
                        style={{
                            width: '100%',
                            padding: '10px',
                            borderRadius: '4px',
                            border: '1px solid #ccc',
                            outline: 'none'
                        }}
                    />
                </div>

                <div style={{ marginBottom: '15px' }}>
                    <label style={{ marginBottom: '5px', color: '#555' }}>Correo:</label>
                    <input
                        type="email"
                        placeholder="Correo"
                        value={correo}
                        onChange={(e) => setCorreo(e.target.value)}
                        style={{
                            width: '100%',
                            padding: '10px',
                            borderRadius: '4px',
                            border: '1px solid #ccc',
                            outline: 'none'
                        }}
                    />
                </div>

                <div style={{ marginBottom: '15px' }}>
                    <label style={{ marginBottom: '5px', color: '#555' }}>Contraseña:</label>
                    <input
                        type="password"
                        placeholder="Contraseña"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        style={{
                            width: '100%',
                            padding: '10px',
                            borderRadius: '4px',
                            border: '1px solid #ccc',
                            outline: 'none'
                        }}
                    />
                </div>

                <button type="submit" style={{
                    padding: '10px',
                    borderRadius: '4px',
                    backgroundColor: '#4CAF50',
                    color: 'white',
                    border: 'none',
                    cursor: 'pointer',
                    fontWeight: 'bold',
                    marginTop: '10px'
                }}>
                    Registrar
                </button>

                {error && <p style={{ color: 'red', textAlign: 'center', marginTop: '10px' }}>{error}</p>}
            </form>
        </div>
    );
}

export default Register;
