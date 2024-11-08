import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../styles/NavBar.css';

function NavBar() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [showDropdown, setShowDropdown] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');
        setIsLoggedIn(!!token);
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('token');
        setIsLoggedIn(false);
        navigate('/login');
    };

    return (
        <nav className="navbar">
            <div className="navbar-logo">
                <Link to="/dashboard" className="logo-text">FINANZANAS</Link>
            </div>
            <div className="navbar-menu">
                <Link to="/" className="navbar-btn">Home</Link>
                <Link to="/help" className="navbar-btn">Ayuda</Link>
                {isLoggedIn ? (
                    <>
                        <Link to="/profile" className="navbar-btn">Perfil</Link>
                        <button className="navbar-btn logout-btn" onClick={handleLogout}>
                            Cerrar sesión
                        </button>
                    </>
                ) : (
                    <>
                        <Link to="/login" className="navbar-btn">Iniciar sesión</Link>
                        <Link to="/register" className="navbar-btn">Registrarse</Link>
                    </>
                )}
            </div>
            <div className="navbar-hamburger" onClick={() => setShowDropdown(!showDropdown)}>
                <div className="menu-icon">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
                {showDropdown && (
                    <div className="dropdown-menu">
                        <Link to="/settings" className="dropdown-item">Configuraciones</Link>
                        <Link to="/about" className="dropdown-item">Acerca de</Link>
                        {isLoggedIn && (
                            <button className="dropdown-item logout-btn" onClick={handleLogout}>
                                Cerrar sesión
                            </button>
                        )}
                    </div>
                )}
            </div>
        </nav>
    );
}

export default NavBar;
