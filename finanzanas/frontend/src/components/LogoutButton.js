import React from 'react';
import { useNavigate } from 'react-router-dom';
function LogoutButton() {
    const navigate = useNavigate(); // Reemplaza useHistory con useNavigate

    const handleLogout = () => {
        // Eliminar el token del localStorage
        localStorage.removeItem('token');

        // Redirigir al usuario a la página de inicio de sesión
        window.alert("Sesión cerrada");
        navigate('/login');

    };

    return (
        <button onClick={handleLogout}>
            Cerrar sesión
        </button>
    );
}

export default LogoutButton;
