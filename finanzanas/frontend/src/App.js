// import {Router} from "react-router-dom";
import {BrowserRouter as Router, Route, Routes, useLocation} from 'react-router-dom';
import logo from './logo.svg';
import './App.css';
import Home from "./components/Home";
import LoginForm from "./components/LoginForm";
import Register from "./components/Register";
import HomePage from "./components/HomePage";
import LoginPage from "./components/LoginPage";
import Logout from "./components/Logout";
import Navbar from "./components/NavBar";

function App() {
    // Hook para obtener la ubicación actual
    const location = useLocation();
    return (
        <div className="App">
            {/*<Router>*/}
                {/*<Navbar/>*/}
                {/* Mostrar la navbar solo si no estamos en la ruta '/' o '/home' */}
                {/*{location.pathname !== '/' && location.pathname !== '/home' && <Navbar />}*/}
                {location.pathname !== '/' && <Navbar />}
                <Routes>
                    <Route path="/"  element={<HomePage/>} />
                    <Route path="/login" element={<LoginPage/>} />
                    <Route path="/home" element={<Home/>} />
                    <Route path="/register" element={<Register />} />

                    {/* Ruta de cierre de sesión */}
                    <Route path="/logout" component={Logout} />

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
