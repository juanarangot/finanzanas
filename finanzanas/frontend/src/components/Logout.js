// src/components/Logout.js

import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function Logout() {
    const navigate = useNavigate(); // Reemplaza useHistory con useNavigate

    useEffect(() => {
        // Eliminar el token del localStorage
        localStorage.removeItem('token');

        // Redirigir al usuario a la página de inicio de sesión
        navigate('/login');
    }, [navigate]);

    return null; // O puedes mostrar un mensaje de "Cerrando sesión..."
}

export default Logout;
