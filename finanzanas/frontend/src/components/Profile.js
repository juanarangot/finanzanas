import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Profile = () => {
    // Estado para almacenar los datos del usuario
    const [userData, setUserData] = useState({
        nombre: '',
        correo: '',
        password: '',
        balance: '',
        objetivoFinanciero: ''
    });

    const [isLoading, setIsLoading] = useState(true); // Para mostrar un mensaje de carga mientras se obtienen los datos

    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');

    useEffect(() => {
        // Obtener los datos del usuario
        axios.get(`http://localhost:8080/api/usuarios/${userId}`, {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(response => {
                setUserData({
                    nombre: response.data.nombre,
                    correo: response.data.correo,
                    balance: response.data.balance,
                    objetivoFinanciero: response.data.objetivoFinanciero
                });
                setIsLoading(false);
            })
            .catch(error => console.error(error));
    }, [userId, token]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setUserData(prevState => ({ ...prevState, [name]: value }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const userUpdateData = { ...userData };
        if (!userUpdateData.password) {
            delete userUpdateData.password;
        }
        axios.put(`http://localhost:8080/api/usuarios/actualizar/${userId}`, userUpdateData, {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(response => {
                alert('Usuario actualizado exitosamente');
                setUserData(prevData => ({ ...prevData, password: '' })); // Limpia el campo de contraseña después de actualizar

                // Redirigir al usuario a la página principal o dashboard
                window.location.href = '/dashboard';
            })
            .catch(error => {
                console.error(error);
                alert('Error al actualizar el usuario');
            });
    };

    if (isLoading) return <p>Cargando datos...</p>;

    return (
        <div style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
            <h2>Perfil de Usuario</h2>
            <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
                <div>
                    <label>Nombre:</label>
                    <input
                        type="text"
                        name="nombre"
                        value={userData.nombre}
                        onChange={handleChange}
                        style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ccc' }}
                    />
                </div>
                <div>
                    <label>Correo:</label>
                    <input
                        type="email"
                        name="correo"
                        value={userData.correo}
                        onChange={handleChange}
                        style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ccc' }}
                    />
                </div>
                <div>
                    <label>Contraseña:</label>
                    <input
                        type="password"
                        name="password"
                        value={userData.password}
                        onChange={handleChange}
                        placeholder="Ingrese nueva contraseña (opcional)"
                        style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ccc' }}
                    />
                </div>
                <div>
                    <label>Balance:</label>
                    <input
                        type="number"
                        name="balance"
                        value={userData.balance}
                        onChange={handleChange}
                        style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ccc' }}
                    />
                </div>
                <div>
                    <label>Objetivo Financiero:</label>
                    <input
                        type="text"
                        name="objetivoFinanciero"
                        value={userData.objetivoFinanciero}
                        onChange={handleChange}
                        style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #ccc' }}
                    />
                </div>
                <button type="submit" style={{
                    padding: '10px', backgroundColor: '#4CAF50', color: 'white', border: 'none',
                    borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold'
                }}>
                    Actualizar Perfil
                </button>
            </form>
        </div>
    );
};

export default Profile;
