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

CREATE TABLE tabla1 (
    producto VARCHAR(20),
    fecha DATE,
    cantidad INT
);

CREATE TABLE tabla2 (
    producto VARCHAR(20),
    fecha DATE,
    cantidad INT
);

-- tabla1: datos para producto A en distintos meses
INSERT INTO tabla1 (producto, fecha, cantidad) VALUES
('A', '2025-01-10', 10),
('A', '2025-02-15', 5),
('A', '2025-03-20', 8),
('A', '2025-04-05', 12);

-- tabla2: datos para producto B en distintos meses
INSERT INTO tabla2 (producto, fecha, cantidad) VALUES
('A', '2025-01-10', 10),
('A', '2025-02-15', 5),
('A', '2025-03-20', 8),
('A', '2025-04-05', 12);


-- ej 8 datos
CREATE TABLE Clubes (
    Codigo INT PRIMARY KEY,
    Nombre VARCHAR(100)
);

CREATE TABLE Jugadoras (
    Legajo INT PRIMARY KEY,
    Nombre VARCHAR(100),
    Apellido VARCHAR(100),
    DNI VARCHAR(20)
);

CREATE TABLE Pases (
    Legajo INT,
    FechaDesde DATE,
    CodigoClub INT,
    PRIMARY KEY (Legajo, FechaDesde),
    FOREIGN KEY (Legajo) REFERENCES Jugadoras(Legajo),
    FOREIGN KEY (CodigoClub) REFERENCES Clubes(Codigo)
);

-- Clubes
INSERT INTO Clubes (Codigo, Nombre) VALUES
(1, 'River Plate'),
(2, 'Boca Juniors'),
(3, 'San Lorenzo');

-- Jugadoras
INSERT INTO Jugadoras (Legajo, Nombre, Apellido, DNI) VALUES
(101, 'Carla', 'Gómez', '12345678'),
(102, 'Lucía', 'Martínez', '87654321');

-- Pases
-- Carla jugó en River desde 2021-01-01 hasta que se fue a Boca en 2022-05-10, y luego sigue ahí
INSERT INTO Pases (Legajo, FechaDesde, CodigoClub) VALUES
(101, '2021-01-01', 1),
(101, '2022-05-10', 2);

-- Lucía jugó en San Lorenzo desde 2020-06-01 y nunca se transfirió
INSERT INTO Pases (Legajo, FechaDesde, CodigoClub) VALUES
(102, '2020-06-01', 3);


-- Modelo 10
INSERT INTO Clubes (Codigo, Nombre) VALUES
(100, 'Boca Juniors'),
(101, 'River Plate'),
(102, 'San Lorenzo'),
(103, 'Independiente');

INSERT INTO Jugadoras (Legajo, Nombre, Apellido, DNI) VALUES
(1, 'Lucía', 'Pérez', '12345678'),
(2, 'Mariana', 'Gómez', '23456789'),
(3, 'Carla', 'Fernández', '34567890');

-- Jugadora 1: dos pases sin superposición
INSERT INTO Pases (Legajo, FechaDesde, FechaHasta, CodigoClub)
VALUES (1, '2022-01-01', '2022-12-31', 100);

INSERT INTO Pases (Legajo, FechaDesde, FechaHasta, CodigoClub)
VALUES (1, '2023-01-01', '2023-12-31', 101);

-- Jugadora 2: pase sin FechaHasta (en curso)
INSERT INTO Pases (Legajo, FechaDesde, FechaHasta, CodigoClub)
VALUES (2, '2024-01-01', NULL, 102);

-- Se superpone con el primer pase de jugadora 1
INSERT INTO Pases (Legajo, FechaDesde, FechaHasta, CodigoClub)
VALUES (1, '2022-06-01', '2022-12-01', 102); -- ❌ Superposición

-- Se superpone con el segundo pase de jugadora 1
INSERT INTO Pases (Legajo, FechaDesde, FechaHasta, CodigoClub)
VALUES (1, '2023-06-01', '2024-01-01', 103); -- ❌ Superposición

-- Se superpone con el pase abierto de jugadora 2
INSERT INTO Pases (Legajo, FechaDesde, FechaHasta, CodigoClub)
VALUES (2, '2024-05-01', '2024-12-31', 101); -- ❌ Superposición

-- Este no funciona, no permito modificar el pasado. 
UPDATE Pases
SET FechaHasta = '2022-11-30'
WHERE Legajo = 1 AND FechaDesde = '2022-01-01';

UPDATE Pases
SET FechaHasta = '2025-06-06', FechaDesde = '2025-06-03'
WHERE Legajo = 2 AND CodigoClub = 102
 
INSERT INTO Pases
VALUES (2, '2024-01-04', NULL, 103)
