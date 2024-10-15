const handleSubmit = async (e) => {
    e.preventDefault();
    try {
        const response = await axios.post('http://localhost:8080/api/auth/login', {
            correo,
            password
        });
        const token = response.data.token; // Supongamos que el token viene en response.data.token
        localStorage.setItem('token', token);
        alert('Login exitoso');
    } catch (error) {
        alert('Error en el login');
        console.error(error);
    }
};
