-- CREATE DATABASE DonacionAlimentos;
-- USE DonacionAlimentos;


CREATE TABLE Direccion (
    id_direccion INT AUTO_INCREMENT PRIMARY KEY,
    calle VARCHAR(200) NOT NULL,
    numero VARCHAR(20),
    colonia VARCHAR(200) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    estado VARCHAR(100) NOT NULL,
    codigo_postal VARCHAR(10) NOT NULL
);


CREATE TABLE Donador (
    id_donador INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    apellido_paterno VARCHAR(200) NOT NULL,
    apellido_materno VARCHAR(200),
    tipo_donador VARCHAR(75) NOT NULL,
    correo_electronico VARCHAR(150) UNIQUE, 
    telefono VARCHAR(20) NOT NULL,
    id_direccion INT,
    FOREIGN KEY (id_direccion) REFERENCES Direccion(id_direccion) 
);


CREATE TABLE Organizacion (
    id_organizacion INT AUTO_INCREMENT PRIMARY KEY,
    nombre_organizacion VARCHAR(150) NOT NULL,
    nombre_responsable VARCHAR(200) NOT NULL,
    correo_electronico VARCHAR(150) UNIQUE, 
    telefono VARCHAR(20) NOT NULL,
    id_direccion INT,
    FOREIGN KEY(id_direccion) REFERENCES Direccion(id_direccion) 
);


CREATE TABLE Alimento (
    id_alimento INT AUTO_INCREMENT PRIMARY KEY,
    nombre_alimento VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    cantidad_disponible DECIMAL(10, 2) NOT NULL, 
    fecha_caducidad DATE NOT NULL,
    id_donador INT,
    FOREIGN KEY (id_donador) REFERENCES Donador (id_donador)
);


CREATE TABLE Entrega (
    id_entrega INT AUTO_INCREMENT PRIMARY KEY,
    fecha_entrega DATETIME NOT NULL, 
    estado_entrega ENUM('pendiente', 'en transito', 'completada', 'cancelada') NOT NULL,
    id_organizacion INT,
    FOREIGN KEY (id_organizacion) REFERENCES Organizacion (id_organizacion) ON DELETE SET NULL
);


CREATE TABLE Detalle_Entrega (
    id_detalle_entrega INT AUTO_INCREMENT PRIMARY KEY,
    id_entrega INT,
    id_alimento INT,
    cantidad_entregada DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_entrega) REFERENCES Entrega (id_entrega),
    FOREIGN KEY (id_alimento) REFERENCES Alimento (id_alimento)
);