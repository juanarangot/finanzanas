import React from 'react';
import { Link } from 'react-router-dom';

function Home() {
    return (
        <div>
            <h1>Bienvenido a Finanzanas</h1>
            <nav>
                <ul>
                    <li>
                        <Link to="/login">Iniciar sesión</Link>
                    </li>
                    <li>
                        <Link to="/register">Registrarse</Link>
                    </li>
                </ul>
            </nav>
        </div>
    );
}

export default Home;
