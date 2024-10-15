// importo el login para poder usarlo en el componente

import React from 'react';
import LoginForm from "./LoginForm";


function LoginPage() {
    return (
        <div>
            <h1>L O G I N</h1>
            <LoginForm/>
            {/*    AGREGO UN LINK PARA EL REGISTRO*/}
            <p>¿No tienes cuenta?</p>
            <a href="/register">Registrarse</a>
        </div>
    );

}

export default LoginPage;