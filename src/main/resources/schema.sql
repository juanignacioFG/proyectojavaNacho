CREATE DATABASE reservas_restaurante;

USE reservas_restaurante;

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    telefono VARCHAR(15) NOT NULL
);

CREATE TABLE reservas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    fecha_hora DATETIME NOT NULL,
    numero_comensales INT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);