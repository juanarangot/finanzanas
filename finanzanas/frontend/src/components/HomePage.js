import React from 'react';
import '../styles/HomePage.css';
import { FaFacebook, FaInstagram, FaTwitter } from 'react-icons/fa';

const HomePage = () => {
    return (
        <div className="homepage">
            <header className="navbar">
                <div className="logo">FINANZANAS</div>
                <nav>
                    <ul>
                        <li><a href="#home">Home</a></li>
                        <li><a href="#about">About Us</a></li>
                        <li><a href="#services">Services</a></li>
                        <li><a href="#contact">Contact Us</a></li>
                        <li><a href="/login" className="button-link">Ingresa</a></li>
                    </ul>
                </nav>
            </header>

            <section className="hero-section">
                <div className="hero-content">
                    <h1>Planeación Financiera Para TODOS</h1>
                    <p className="hero-subtitle">Te ayudamos a tener unas finanzas más sanas</p>
                    <p className="hero-description">
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Enhance your financial knowledge and prepare for a secure future with us.
                    </p>
                    <div className="social-icons">
                        <a href="https://facebook.com" className="social-link" aria-label="Facebook">
                            <FaFacebook />
                        </a>
                        <a href="https://instagram.com" className="social-link" aria-label="Instagram">
                            <FaInstagram />
                        </a>
                        <a href="https://twitter.com" className="social-link" aria-label="Twitter">
                            <FaTwitter />
                        </a>
                    </div>
                    <button className="cta-button">Know More</button>
                </div>
            </section>
        </div>
    );
};

export default HomePage;
