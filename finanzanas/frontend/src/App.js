// import {Router} from "react-router-dom";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import logo from './logo.svg';
import './App.css';
import Home from "./components/Home";
import LoginForm from "./components/LoginForm";
import Register from "./components/Register";
import HomePage from "./components/Home2";
import LoginPage from "./components/LoginPage";

function App() {
    return (
        <div className="App">
            {/*<h1>Bienvenido a Finanzanas</h1>*/}
            {/*<LoginForm/>*/}
            {/*<Home/>*/}
            <Router>
                {/*<Routes>*/}
                {/*    <Route path="/" element={<Home />} />*/}
                {/*    <Route path="/login" element={<LoginForm />} />*/}
                {/*    <Route path="/register" element={<Register />} />*/}
                {/*</Routes>*/}
                <Routes>
                    <Route path="/"  element={<HomePage/>} />
                    <Route path="/login" element={<LoginPage/>} />
                        <Route path="/register" element={<Register />} />
                    {/*<Route path="/about" component={AboutPage} />*/}
                    {/*<Route path="/services" component={ServicesPage} />*/}
                    {/*<Route path="/contact" component={ContactPage} />*/}
                </Routes>
            </Router>
        </div>
    )
        ;
}

export default App;
