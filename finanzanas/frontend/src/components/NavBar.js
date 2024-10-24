import React from 'react';
import LogoutButton from './LogoutButton';


/*
*  Componente de barra de navegación
*
* */
function Navbar() {
    return (
        <nav>
            {/* Otros enlaces de navegación */}
            <LogoutButton />
        </nav>
    );
}

export default Navbar;
