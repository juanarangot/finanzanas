// import {Router} from "react-router-dom";
import {BrowserRouter as Router, Navigate, Route, Routes, useLocation} from 'react-router-dom';
import logo from './logo.svg';
import './App.css';
import Home from "./components/Home";
import LoginForm from "./components/LoginForm";
import Register from "./components/Register";
import HomePage from "./components/HomePage";
import LoginPage from "./components/LoginPage";
import Logout from "./components/Logout";
import Navbar from "./components/NavBar";
import NotFound from "./components/NotFound";
import Dashboard from "./components/Dashboard";
import Profile from "./components/Profile";

function App() {
    // Hook para obtener la ubicación actual
    const location = useLocation();

    // Verifica si la ubicación actual es la ruta 404
    const hideNavbar = location.pathname === '/404' || location.pathname === '*';
    // const hideNavbar = location.pathname === '/404';

    return (
        <div className="App">
            {/*<Router>*/}
                {/*<Navbar/>*/}
                {/* Mostrar la navbar solo si no estamos en la ruta '/' o '/home' */}
                {/*{location.pathname !== '/' && location.pathname !== '/home' && <Navbar />}*/}
                {!hideNavbar && location.pathname !== '/' && <Navbar />}
                {/* Mostrar la navbar solo si showNavbar es true */}
                {/*{!hideNavbar && showNavbar && <Navbar />}*/}
                <Routes>

                    <Route path="/"  element={<HomePage/>} />
                    <Route path="/login" element={<LoginPage/>} />
                    <Route path="/home" element={<Home/>} />
                    <Route path="/register" element={<Register />} />
                    {/* Ruta de cierre de sesión */}
                    <Route path="/logout" component={Logout} />
                    <Route path="/dashboard" element={<Dashboard />} />
                    <Route path="/profile" element={<Profile />} />


                    {/* Ruta explícita para la página 404 */}
                    <Route path="/404" element={<NotFound />} />
                    {/* Ruta 404 para cualquier ruta que no coincida, se redirige a /404 */}
                    <Route path="*" element={<Navigate to="/404" />} />


                    {/*Ejemplo de ruta privada*/}
                    {/*<PrivateRoute path="/dashboard" component={Dashboard} />*/}

                    {/*<Route path="/about" component={AboutPage} />*/}
                    {/*<Route path="/services" component={ServicesPage} />*/}
                    {/*<Route path="/contact" component={ContactPage} />*/}
                </Routes>
            {/*</Router>*/}
        </div>
    )
        ;
}

// export default App;
// Se exporta el componente AppWrapper en lugar de App para que el componente App esté envuelto en el Router
// y asi poder usar la navbar de forma mas dinamica
export default function AppWrapper() {
    return (
        <Router>
            <App />
        </Router>
    );
}
