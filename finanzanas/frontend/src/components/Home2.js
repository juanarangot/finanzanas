import React from 'react';
import '../styles/HomePage.css'; // Importamos el archivo CSS para el estilo

const HomePage = () => {
    return (
        <div className="homepage">
            <header className="navbar">
                <div className="logo">LOGO</div>
                <nav>
                    <ul>
                        <li><a href="#home">Home</a></li>
                        <li><a href="#about">About Us</a></li>
                        <li><a href="#services">Services</a></li>
                        <li><a href="#contact">Contact Us</a></li>
                    </ul>
                </nav>
            </header>

            <section className="hero-section">
                <div className="hero-content">
                    <h1>Financial Planning</h1>
                    <p>Preparation is the Key</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                    <div className="social-icons">
                        <a href="https://facebook.com">Facebook</a>
                        <a href="https://instagram.com">Instagram</a>
                        <a href="https://twitter.com">Twitter</a>
                    </div>
                    <button className="cta-button">Know More</button>
                </div>
                <div className="hero-image">
                    <img src="/path-to-image.jpg" alt="Financial Planning" />
                </div>
            </section>
        </div>
    );
};

export default HomePage;
