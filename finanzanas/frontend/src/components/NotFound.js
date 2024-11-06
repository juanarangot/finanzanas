import React from 'react';
import '../styles/NotFound.css'; // para estilos personalizados

const NotFound = () => {
    return (
        <div className="not-found-container">
            <div className="wallet-image">
                {/* Aquí puedes agregar la imagen de la cartera vacía */}
                <img src="/wallet.jpg" alt="Empty wallet illustration" />
            </div>
            <h1>Oops! 404</h1>
            <p>La página que buscas no existe o está vacía, al igual que esta cartera...</p>
            <button onClick={() => window.location.href = '/'} className="home-button">Regresar al Inicio</button>
        </div>
    );
};

export default NotFound;
