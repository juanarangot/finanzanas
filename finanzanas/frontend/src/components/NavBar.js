import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../styles/NavBar.css'; // Añade estilos CSS aquí

function NavBar() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const navigate = useNavigate();

    // Verificar si el usuario tiene una sesión activa
    useEffect(() => {
        const token = localStorage.getItem('token');
        setIsLoggedIn(!!token); // Establecer el estado dependiendo de si el token existe
    }, []);

    // Función para manejar el cierre de sesión
    const handleLogout = () => {
        localStorage.removeItem('token'); // Eliminar token de localStorage
        setIsLoggedIn(false); // Actualizar estado
        navigate('/login'); // Redirigir a la página de login
    };

    return (
        <nav className="navbar">
            <div className="navbar-logo">
                <Link to="/">FINANZANAS</Link>
            </div>
            <div className="navbar-menu">
                <Link to="/help">Ayuda</Link>
                {isLoggedIn && (
                    <>
                        <Link to="/profile">Perfil</Link>
                        <button className="logout-btn" onClick={handleLogout}>
                            Cerrar sesión
                        </button>
                    </>
                )}
                {!isLoggedIn && <Link to="/login">Iniciar sesión</Link>}
            </div>
            {/* Menú hamburguesa con opciones */}
            <div className="navbar-hamburger">
                <div className="menu-icon">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
                <div className="dropdown-menu">
                    <Link to="/settings">Configuraciones</Link>
                    <Link to="/about">Acerca de</Link>
                </div>
            </div>
        </nav>
    );
}

export default NavBar;
