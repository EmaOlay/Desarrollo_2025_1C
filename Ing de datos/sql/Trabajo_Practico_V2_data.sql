-- Crear la base de datos TP
CREATE DATABASE Trabajo_practico;
GO

-- Usar la base de datos TP
USE Trabajo_practico;


go
-- Crear la tabla personas SIN la restricción de clave primaria en la columna idcreate table personas (id int, nombre varchar(50) not null, apellido varchar(50) not null);;
GO

-- Insertar 20 registros de ejemplo en la tabla personas (ahora 'id' puede repetirse)
INSERT INTO personas (id, nombre, apellido) VALUES
(1, 'Ana', 'García'),
(2, 'Juan', 'Pérez'),
(3, 'Laura', 'Martínez'),
(4, 'Carlos', 'López'),
(5, 'Sofía', 'Rodríguez'),
(6, 'Pedro', 'Sánchez'),
(7, 'Valentina', 'Fernández'),
(8, 'Mateo', 'Díaz'),
(9, 'Isabella', 'González'),
(10, 'Lucas', 'Ruiz'),
(1, 'Martina', 'Vargas'), -- 'id' repetido
(2, 'Sebastián', 'Castro'), -- 'id' repetido
(2, 'Joaquin', 'Del Vequio'), -- 'id' repetido
(13, 'Emilia', 'Jiménez'),
(14, 'Diego', 'Torres'),
(15, 'Camila', 'Rojas'),
(16, 'Nicolás', 'Flores'),
(17, 'Florencia', 'Vega'),
(18, 'Benjamín', 'Herrera'),
(19, 'Antonella', 'Silva'),
(20, 'Gabriel', 'Núñez');
GO
