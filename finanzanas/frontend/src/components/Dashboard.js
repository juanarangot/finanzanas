import axios from 'axios';
import React, { useEffect, useState } from 'react';

const Dashboard = () => {
    const [balance, setBalance] = useState(0.00);
    const [transactions, setTransactions] = useState([]);
    const [budgets, setBudgets] = useState([]);
    const [userName, setUserName] = useState('');
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');

    console.log('Token guardado:', token);
    console.log('ID de usuario guardado:', userId);

    // Configuración de cabeceras para cada solicitud
    const axiosConfig = {
        headers: {
            Authorization: `Bearer ${token}`
        }
    };

    useEffect(() => {
        if (!token) {
            console.error("Token no disponible");
            return;
        }

        // Obtener nombre del usuario
        axios.get(`http://localhost:8080/api/usuarios/${userId}`, axiosConfig)
            .then(response => setUserName(response.data.nombre))
            .catch(error => console.error("Error al obtener el nombre:", error));

        // Obtener balance del usuario
        axios.get(`http://localhost:8080/api/usuarios/${userId}/balance`, axiosConfig)
            .then(response => setBalance(response.data))
            .catch(error => console.error("Error al obtener el balance:", error));

        // Cargar transacciones desde localStorage si están disponibles
        const storedTransactions = JSON.parse(localStorage.getItem('transactions'));
        if (storedTransactions) {
            setTransactions(storedTransactions);
        } else {
            // Obtener transacciones del usuario desde la API y guardarlas en localStorage
            axios.get(`http://localhost:8080/api/usuarios/${userId}/movimientos`, axiosConfig)
                .then(response => {
                    setTransactions(response.data);
                    localStorage.setItem('transactions', JSON.stringify(response.data)); // Guardar en localStorage
                })
                .catch(error => console.error("Error al obtener transacciones:", error));
        }

        // Obtener presupuestos del usuario
        axios.get(`http://localhost:8080/api/usuarios/${userId}/presupuestos`, axiosConfig)
            .then(response => setBudgets(response.data))
            .catch(error => console.error("Error al obtener presupuestos:", error));
    }, [userId, token]);

    const addTransaction = (tipo) => {
        const monto = parseFloat(prompt(`Ingrese el monto para el ${tipo.toLowerCase()}:`));
        const descripcion = prompt("Ingrese una descripción:");

        if (!isNaN(monto) && descripcion) {
            axios.post(`http://localhost:8080/api/movimientos/crear`, {
                tipo,
                monto,
                descripcion,
                userId
            }, axiosConfig).then(response => {
                alert(`Transacción de ${tipo.toLowerCase()} agregada exitosamente.`);
                const updatedTransactions = [...transactions, response.data];
                setTransactions(updatedTransactions);
                setBalance(prevBalance => tipo === "INGRESO" ? prevBalance + monto : prevBalance - monto);

                // Actualizar localStorage con la nueva lista de transacciones
                localStorage.setItem('transactions', JSON.stringify(updatedTransactions));
            }).catch(error => {
                alert("Error al agregar la transacción.");
                console.error(error);
            });
        } else {
            alert("Monto o descripción inválidos.");
        }
    };

    const downloadReport = (type) => {
        const emptyFile = new Blob([], { type: type === 'pdf' ? 'application/pdf' : 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        const url = window.URL.createObjectURL(emptyFile);
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', `Reporte.${type}`);
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);
    };

    return (
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', padding: '20px', backgroundColor: '#f5f7fa', minHeight: '100vh' }}>
            <h1 style={{ color: '#333', marginBottom: '10px' }}>Dashboard</h1>
            <h2 style={{ color: '#4CAF50', marginBottom: '20px' }}>Bienvenido, {userName}</h2>

            <div style={{ display: 'flex', flexWrap: 'wrap', gap: '20px', justifyContent: 'center', width: '100%', maxWidth: '1000px' }}>
                <div style={{
                    padding: '20px', borderRadius: '8px', boxShadow: '0px 4px 12px rgba(0, 0, 0, 0.1)',
                    backgroundColor: '#ffffff', width: '100%', maxWidth: '300px', textAlign: 'center'
                }}>
                    <h2 style={{ color: '#4CAF50' }}>Balance</h2>
                    <p style={{ fontSize: '1.5em', fontWeight: 'bold', color: '#333' }}>${balance}</p>
                </div>

                <div style={{
                    padding: '20px', borderRadius: '8px', boxShadow: '0px 4px 12px rgba(0, 0, 0, 0.1)',
                    backgroundColor: '#ffffff', width: '100%', maxWidth: '300px'
                }}>
                    <h3 style={{ color: '#333' }}>Movimientos Financieros</h3>
                    <ul style={{ listStyleType: 'none', padding: '0', maxHeight: '150px', overflowY: 'auto' }}>
                        {transactions.map((transaction, index) => (
                            <li key={index} style={{ margin: '10px 0', color: transaction.tipo === 'INGRESO' ? '#4CAF50' : '#F44336' }}>
                                <strong>{transaction.tipo}</strong>: ${transaction.monto} - {transaction.descripcion}
                            </li>
                        ))}
                    </ul>
                </div>

                <div style={{
                    padding: '20px', borderRadius: '8px', boxShadow: '0px 4px 12px rgba(0, 0, 0, 0.1)',
                    backgroundColor: '#ffffff', width: '100%', maxWidth: '300px'
                }}>
                    <h3 style={{ color: '#333' }}>Presupuestos</h3>
                    <ul style={{ listStyleType: 'none', padding: '0', maxHeight: '150px', overflowY: 'auto' }}>
                        {budgets.map((budget, index) => (
                            <li key={index} style={{ margin: '10px 0' }}>
                                <strong>{budget.nombre}</strong>: ${budget.monto} - {budget.descripcion}
                            </li>
                        ))}
                    </ul>
                </div>
            </div>

            <div style={{ marginTop: '20px', display: 'flex', gap: '10px' }}>
                <button onClick={() => addTransaction("INGRESO")} style={{
                    padding: '10px 20px', borderRadius: '4px', backgroundColor: '#4CAF50', color: 'white',
                    border: 'none', cursor: 'pointer', fontWeight: 'bold'
                }}>Agregar Ingreso</button>
                <button onClick={() => addTransaction("EGRESO")} style={{
                    padding: '10px 20px', borderRadius: '4px', backgroundColor: '#F44336', color: 'white',
                    border: 'none', cursor: 'pointer', fontWeight: 'bold'
                }}>Agregar Egreso</button>
            </div>

            <div style={{ marginTop: 'auto', display: 'flex', gap: '10px', paddingBottom: '20px' }}>
                <button onClick={() => downloadReport('pdf')} style={{
                    padding: '10px 20px', borderRadius: '4px', backgroundColor: '#4CAF50', color: 'white',
                    border: 'none', cursor: 'pointer', fontWeight: 'bold'
                }}>Download PDF</button>
                <button onClick={() => downloadReport('xlsx')} style={{
                    padding: '10px 20px', borderRadius: '4px', backgroundColor: '#2196F3', color: 'white',
                    border: 'none', cursor: 'pointer', fontWeight: 'bold'
                }}>Download Excel</button>
            </div>
        </div>
    );
};

export default Dashboard;
