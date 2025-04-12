GO 
USE [UADE_A_01];

IF NOT EXISTS (
    SELECT
        *
    FROM
        sysobjects
    WHERE
        NAME = 'jugadoras'
        ANd xtype = 'U'
) BEGIN
CREATE TABLE
    jugadoras (
        Nombre TEXT,
        Apellido TEXT,
        FechaNac DATE,
        Domicilio TEXT,
        DNI INT,
        codJugadora INT PRIMARY KEY
    );

INSERT INTO
    jugadoras
VALUES
    (
        'asd',
        'asd123',
        '2025-12-31',
        'por ahi...',
        123456789,
        1
    );

END

-- CREATE TABLE partidos (
--     puntosObtenidos INT,
--     descClub TEXT,
--     codCampeonato INT,
--     fechacomienzo DATETIME,
--     codClub1 INT, -- FOREIGN KEY(jugadoras.Clubes.codClub),
--     codClub2 INT,
--     fechaPartido DATE,
--     golesClub1 INT,
--     golesClub2 INT,
--     -- PRIMARY KEY CLUSTERED(fechacomienzo,codClub1,codClub2)
-- );
-- CREATE TABLE Clubes(
--     NombreClub TEXT,
--     fecFundacion DATE,
--     domicilio TEXT,
--     codClub INT PRIMARY KEY,
--     codJugadora INT
-- );
-- Campeonatos??