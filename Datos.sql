-- Tabla de Perfiles
CREATE TABLE Perfil (
    id_perfil INT PRIMARY KEY AUTO_INCREMENT,
    nombre_perfil VARCHAR(50),
    permisos TEXT
);

-- Tabla de Usuarios
CREATE TABLE Usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    correo VARCHAR(100) UNIQUE NOT NULL,
    fecha_nacimiento DATE,
    id_perfil INT,
    FOREIGN KEY (id_perfil) REFERENCES Perfil(id_perfil)
);

-- Tabla de Categorías Financieras
CREATE TABLE CategoriaFinanciera (
    id_categoria INT PRIMARY KEY AUTO_INCREMENT,
    nombre_categoria VARCHAR(50)
);

-- Tabla de Transacciones Financieras
CREATE TABLE TransaccionFinanciera (
    id_transaccion INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    monto DECIMAL(10, 2) NOT NULL,
    fecha DATE NOT NULL,
    id_categoria INT,
    descripcion TEXT,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_categoria) REFERENCES CategoriaFinanciera(id_categoria)
);

-- Tabla de Transacciones de la Aplicación
CREATE TABLE TransaccionAplicacion (
    id_transaccion_app INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    accion VARCHAR(100) NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

-- Tabla de Objetivos Financieros (opcional)
CREATE TABLE ObjetivoFinanciero (
    id_objetivo INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    nombre_objetivo VARCHAR(100),
    monto_objetivo DECIMAL(10, 2),
    fecha_meta DATE,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

-- Tabla de Presupuestos (opcional)
CREATE TABLE Presupuesto (
    id_presupuesto INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    monto_maximo DECIMAL(10, 2),
    fecha_inicio DATE,
    fecha_fin DATE,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

-- Tabla de Reportes Financieros (opcional)
CREATE TABLE ReporteFinanciero (
    id_reporte INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    fecha_generacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo_reporte VARCHAR(50),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);
