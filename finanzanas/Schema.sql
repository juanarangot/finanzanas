-- Pasos para crear la base de datos de forma funcional con su usuario y sus tablas
-- Es imporante haber ingresado a mysql como root para poder ejecutar este script

-- e.g mysql "-u root -p < Schema.sql"

-- Creacion de la base de datos
CREATE DATABASE IF NOT EXISTS finanzanas;

-- Creacion del usuario para la base de datos
CREATE USER IF NOT EXISTS 'juanmarato'@'localhost' IDENTIFIED BY 'roma123';

-- Asignar permisos al usuario (solo a la base de datos de finanzanas)
GRANT ALL PRIVILEGES ON finanzanas.* TO 'juanmarato'@'localhost';

-- Seleccionar la base de datos a usar
USE finanzanas;

-- Query para crear la tabla asociada a los usuarios
CREATE TABLE usuarios (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,  -- Nombre del usuario
    correo VARCHAR(255) NOT NULL UNIQUE,  -- Correo del usuario
    password VARCHAR(255) NOT NULL, -- Contraseña del usuario
    INDEX correo_idx (correo)   -- Indice para la columna correo
);


-- query para crear la tabla asociada a los movimientos financieros
CREATE TABLE movimientos (
    id_movimiento BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT,  -- Llave foránea que relaciona con la tabla de usuarios
    tipo_movimiento ENUM('ingreso', 'egreso'),  -- Tipo de movimiento: ingreso o egreso
    monto DECIMAL(10, 2),  -- Cantidad del movimiento
    descripcion VARCHAR(255),  -- Descripción del movimiento (opcional)
    fecha DATETIME,  -- Fecha del movimiento
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);