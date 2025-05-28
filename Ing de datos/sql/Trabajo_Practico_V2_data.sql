-- Crear la base de datos TP
CREATE DATABASE Trabajo_practico_Grupo_1;
GO
-- Usar la base de datos TP
USE Trabajo_practico_Grupo_1;


go
-- Creamos la tabla personas SIN la restricción de clave primaria en la columna idcreate table personas (id int, nombre varchar(50) not null, apellido varchar(50) not null);;
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
/*Fin de datos para el ejercicio 1 y 2*/


/*Inicio de datos para el ejercicio 4*/
use Trabajo_practico_Grupo_1;


go create table valores(id int not null);
/*Caso 1*/
INSERT INTO
    valores
VALUES(1),(2),(4),(5),(6),(8)
/*Caso 2*/
DELETE FROM valores;
INSERT INTO
    valores
VALUES (-3),(-1),(0),(2),(4),(5),(6),(8)
/*Fin de datos para el ejercicio 4*/


/*Inicio datos ejercicio 6*/
use Trabajo_practico_Grupo_1;


go
-- Creamos tabla 1 y tabla 2 con igual estructuracreate table tabla1 (producto varchar(20), fecha date, cantidad int);;create table tabla2 (producto varchar(20), fecha date, cantidad int);;
/*Caso 1 tabla1 y tabla2 son identicas*/
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
/*FIN Caso 1 tabla1 y tabla2 son identicas*/
/*Caso 2 tabla1 y tabla2 NO son identicas*/
DELETE FROM tabla1;
Delete from tabla2;
INSERT INTO tabla1 (producto, fecha, cantidad) VALUES
('A', '2025-01-10', 10),
('A', '2025-02-15', 5),
('A', '2025-03-20', 8),
('A', '2025-04-05', 12);
-- tabla2: datos para producto B en distintos meses
INSERT INTO tabla2 (producto, fecha, cantidad) VALUES
('A', '2025-01-10', 9), -- Diferente cantidad
('A', '2025-02-15', 5),
('A', '2025-03-20', 8),
('A', '2025-04-05', 12);
/*FIN Caso 2 tabla1 y tabla2 NO son identicas*/
/*Fin datos Ejercicio 6*/

-- Datos para el ejercicio 8
use Trabajo_practico_Grupo_1;
go
create table clubes (codigo int primary key, nombre varchar(100));


create table jugadoras (legajo int primary key, nombre varchar(100), apellido varchar(100), dni varchar(20));

-- Default fechaHasta es la fecha de hoy para coincidir con la logica del trigger.
create table pases (legajo int, fechadesde date,FechaHasta date DEFAULT CAST(GETDATE() AS DATE), codigoclub int, primary key (legajo, fechadesde), foreign key (legajo) references jugadoras(legajo), foreign key (codigoclub) references clubes(codigo));
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


/*Datos ejercicio 10 las tablas fuero creadas previamente*/
use Trabajo_practico_Grupo_1;
go
-- Limpio las tablas
DELETE FROM Pases;
DELETE FROM Clubes;
DELETE FROM Jugadoras;

-- Procedo a insertar los datos de ejemplo
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

/*######## ACA ARRANCAN LOS CASOS DE PRUEBA###########*/
-- Prueba 1: Inserción que se superpone completamente con un pase existente (debe fallar)
-- (Jugadora 1 ya tiene pase del '2022-01-01' a '2022-12-31')
INSERT INTO Pases (Legajo, FechaDesde, FechaHasta, CodigoClub)
VALUES (1, '2022-03-01', '2022-09-30', 103);


-- Prueba 2: Inserción que envuelve un pase existente (debe fallar)
-- (Jugadora 1 ya tiene pase del '2022-01-01' a '2022-12-31')
INSERT INTO Pases (Legajo, FechaDesde, FechaHasta, CodigoClub)
VALUES (1, '2021-06-01', '2023-06-30', 102);


-- Prueba 3: Inserción que superpone el final de un pase existente (debe fallar)
-- (Jugadora 1 ya tiene pase del '2022-01-01' a '2022-12-31')
INSERT INTO Pases (Legajo, FechaDesde, FechaHasta, CodigoClub)
VALUES (1, '2022-10-01', '2023-03-31', 100);


-- Prueba 4: Inserción que superpone el inicio de un pase existente (debe fallar)
-- (Jugadora 1 ya tiene pase del '2022-01-01' a '2022-12-31')
INSERT INTO Pases (Legajo, FechaDesde, FechaHasta, CodigoClub)
VALUES (1, '2021-09-01', '2022-04-30', 101);

-- UPDATE 1: Modificación de FechaHasta sin superposición (debe tener éxito)
PRINT 'Intentando UPDATE 1: Extender Pase 1 de Jugadora 1 sin superposición...';
UPDATE Pases
SET FechaHasta = '2022-10-31'
WHERE Legajo = 1 AND FechaDesde = '2022-01-01';

-- UPDATE 2: Modificación de FechaDesde creando superposición (debe fallar)
-- El Pase 1 (2022-01-01 a 2022-12-31) y el Pase 2 (2023-01-01 a 2023-12-31)
-- Intentaremos mover el inicio del Pase 2 a '2022-06-01'
UPDATE Pases
SET FechaDesde = '2022-06-01'
WHERE Legajo = 1 AND FechaDesde = '2023-01-01';

-- UPDATE 3: Cambiar FechaHasta de un pase 'NULL' a una fecha específica
UPDATE Pases
SET FechaHasta = '2024-12-31'
WHERE Legajo = 2 AND FechaDesde = '2024-01-01';