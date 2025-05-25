-- use [trabajo_practico]
/*Dada una tabla PERSONAS que contenga la siguiente estructura, seleccione las filas
 
 
 de la tabla tal que los valores del atributo candidato a ser primary key estén repetidos más de una vez. id (clave candidata) nombre varchar(20) apellido varchar(20)*/
SELECT
    id,
    nombre,
    apellido
FROM
    dbo.personas
WHERE
    id in (
        SELECT
            id
        FROM
            dbo.personas
        GROUP BY
            id
        having
            count(*) > 1
    )
    /*Realice una sentencia DELETE que borre las filas de la tabla PERSONAS del ejercicio
     1 que estén duplicadas dejando sólo una fila con cada valor de la clave candidata.*/
    delete
delete FROM
    dbo.personas
WHERE
    concat(id, nombre, apellido) in (
        SELECT
            concat(id, nombre, apellido)
        FROM
            (
                SELECT
                    id,
                    nombre,
                    apellido,
                    row_number() OVER (
                        PARTITION BY id
                        ORDER BY
                            (
                                SELECT
                                    null
                            )
                    ) as rownum
                FROM
                    personas
            ) as duplicates
        WHERE
            rownum > 1
    );

SELECT
    count(*)
FROM
    dbo.personas
    /*Realizar una consulta que devuelva el monto total comprado por cliente en cada
     provincia de los fabricantes. Las columnas a mostrar son número de cliente, nombre
     del cliente, apellido del cliente, provincia_desc (de los fabricantes) y monto total
     comprado. Ordenar la información por número de cliente en forma ascendente y monto
     total comprado en forma descendente.*/
    --    use [uade_a_01];
SELECT
    c.cliente_num,
    c.nombre,
    c.apellido,
    prov.provincia_desc,
    sum(f.precio_unit * f.cantidad) as monto_total_comprado
FROM
    dbo.clientes c
    join dbo.facturas i ON c.cliente_num = i.cliente_num
    join dbo.facturas_det f ON i.factura_num = f.factura_num
    LEFT JOIN dbo.productos p ON p.producto_cod = f.producto_cod
    LEFT JOIN dbo.fabricantes fa ON p.fabricante_cod = fa.fabricante_cod
    LEFT JOIN dbo.provincias prov ON prov.provincia_cod = fa.provincia_cod
GROUP BY
    c.cliente_num,
    c.nombre,
    c.apellido,
    prov.provincia_desc
ORDER BY
    c.cliente_num asc,
    monto_total_comprado desc;

/*Sea una tabla VALORES que tiene un campo numérico secuencial Id, realizar una
 consulta que obtenga el valor del primer espacio libre de ese campo.*/
CREATE TABLE valores(id int NOT NULL);

INSERT INTO
    valores
VALUES
    (1)
INSERT INTO
    valores
VALUES
    (2)
INSERT INTO
    valores
VALUES
    (4)
INSERT INTO
    valores
VALUES
    (5)
INSERT INTO
    valores
VALUES
    (6)
INSERT INTO
    valores
VALUES
    (8) WITH CTE AS (
        SELECT
            id,
            LAG(id) OVER (
                ORDER BY
                    Id
            ) AS id_anterior
        FROM
            valores
    )
SELECT
    TOP 1 id_anterior + 1 AS primer_espacio_libre
FROM
    CTE
WHERE
    id_anterior IS NOT NULL
    AND id <> id_anterior + 1
ORDER BY
    id_anterior;

/*Mostrar por cada Producto (producto_cod) las cantidades totales mensuales
 vendidas (según la fecha de emisión de la factura). No importa si hay meses de
 diferentes años, se suman como si fueran del mismo año. La información se deberá
 mostrar en forma tabular como se muestra en la figura, ordenada por código de producto*/
SELECT
    d.producto_cod,
    SUM(
        CASE
            WHEN MONTH(f.fecha_emision) = 1 THEN d.cantidad
            ELSE 0
        END
    ) AS Enero,
    SUM(
        CASE
            WHEN MONTH(f.fecha_emision) = 2 THEN d.cantidad
            ELSE 0
        END
    ) AS Febrero,
    SUM(
        CASE
            WHEN MONTH(f.fecha_emision) = 3 THEN d.cantidad
            ELSE 0
        END
    ) AS Marzo,
    SUM(
        CASE
            WHEN MONTH(f.fecha_emision) = 4 THEN d.cantidad
            ELSE 0
        END
    ) AS Abril,
    SUM(
        CASE
            WHEN MONTH(f.fecha_emision) = 5 THEN d.cantidad
            ELSE 0
        END
    ) AS Mayo,
    SUM(
        CASE
            WHEN MONTH(f.fecha_emision) = 6 THEN d.cantidad
            ELSE 0
        END
    ) AS Junio,
    SUM(
        CASE
            WHEN MONTH(f.fecha_emision) = 7 THEN d.cantidad
            ELSE 0
        END
    ) AS Julio,
    SUM(
        CASE
            WHEN MONTH(f.fecha_emision) = 8 THEN d.cantidad
            ELSE 0
        END
    ) AS Agosto,
    SUM(
        CASE
            WHEN MONTH(f.fecha_emision) = 9 THEN d.cantidad
            ELSE 0
        END
    ) AS Septiembre,
    SUM(
        CASE
            WHEN MONTH(f.fecha_emision) = 10 THEN d.cantidad
            ELSE 0
        END
    ) AS Octubre,
    SUM(
        CASE
            WHEN MONTH(f.fecha_emision) = 11 THEN d.cantidad
            ELSE 0
        END
    ) AS Noviembre,
    SUM(
        CASE
            WHEN MONTH(f.fecha_emision) = 12 THEN d.cantidad
            ELSE 0
        END
    ) AS Diciembre
FROM
    facturas f
    JOIN facturas_det d ON f.factura_num = d.factura_num
GROUP BY
    d.producto_cod
ORDER BY
    d.producto_cod;

/*Realizar UN query que asegure que dos tablas con la misma estructura tengan
 exactamente la misma cantidad de filas y exactamente los mismos datos en cada
 columna. Es decir, que las tablas sean exactamente iguales.*/
WITH DIFERENCIAS as(
    SELECT
        *
    FROM
        tabla1
    EXCEPT
    SELECT
        *
    FROM
        tabla2
    UNION
    SELECT
        *
    FROM
        tabla2
    EXCEPT
    SELECT
        *
    FROM
        tabla1
)
SELECT
    CASE
        WHEN EXISTS (
            SELECT
                1
            FROM
                DIFERENCIAS
        ) THEN 'son distintas'
        ELSE 'son iguales'
    END as resultado
    /*Realizar una consulta que tenga el siguiente formato (los datos son solo de ejemplo)
     que muestre para cada cliente que tenga referente los distintos niveles de referentes
     que posee. Cada referente se deberá mostrar en una columna diferente y debe
     mostrar hasta el tercer nivel si es que existe.*/
SELECT
    c1.cliente_num,
    c1.cliente_ref,
    c2.cliente_ref,
    c3.cliente_ref
FROM
    clientes c1
    LEFT JOIN clientes c2 ON c1.cliente_ref = c2.cliente_num
    LEFT JOIN clientes c3 ON c2.cliente_ref = c3.cliente_num
WHERE
    c1.cliente_ref IS NOT NULL
ORDER BY
    c1.cliente_num
    /*Dado el siguiente modelo, realice una consulta que permita averiguar en qué equipo juega
     o jugó una jugadora en una fecha determinada.*/
    WITH fechita as(
        SELECT
            '2023-05-10' as miFechita
    )
SELECT
    j.Nombre,
    j.Apellido,
    c.Nombre AS Club,
    p.FechaDesde
FROM
    Jugadoras j
    JOIN Pases p ON j.Legajo = p.Legajo
    JOIN Clubes c ON p.CodigoClub = c.Codigo
WHERE
    j.Legajo = 101
    AND p.FechaDesde <= (
        SELECT
            miFechita
        FROM
            fechita
    )
    AND NOT EXISTS (
        SELECT
            1
        FROM
            Pases p2
        WHERE
            p2.Legajo = p.Legajo
            AND p2.FechaDesde <= (
                SELECT
                    miFechita
                FROM
                    fechita
            )
            AND p2.FechaDesde > p.FechaDesde
    );

/*Realizar UN query sin utilizar subqueries, que muestre las cantidades de cada producto
 vendidas en cada provincia. La provincia se refiere a la del Fabricante. Mostrar código de
 provincia, código de producto, cantidad vendida del producto en la provincia, cantidad
 total de productos vendidos en la provincia y porcentaje del total vendido en la provincia
 respecto a la cantidad total de productos vendidos en el país.
 Mostrar la información ordenada por provincia en forma ascendente y cantidad total del
 producto vendido en forma descendente.*/
SELECT
    prov.provincia_cod AS Provincia,
    fd.producto_cod AS Producto,
    SUM(fd.cantidad) AS cantidadProductoProvincia,
    SUM(SUM(fd.cantidad)) OVER (PARTITION BY prov.provincia_cod) AS totalProductosProvincia,
    CAST(
        SUM(fd.cantidad) * 100.0 / SUM(SUM(fd.cantidad)) OVER (PARTITION BY prov.provincia_cod) AS INT
    ) AS [% Cant/total por Provincia],
    CAST(
        SUM(SUM(fd.cantidad)) OVER (PARTITION BY prov.provincia_cod) * 100.0 / SUM(SUM(fd.cantidad)) OVER () AS INT
    ) AS [% Total Prov / Total país]
FROM
    facturas f
    LEFT JOIN facturas_det fd ON f.factura_num = fd.factura_num
    LEFT JOIN productos p ON fd.producto_cod = p.producto_cod
    LEFT JOIN fabricantes fab ON fab.fabricante_cod = p.fabricante_cod
    LEFT JOIN provincias prov ON prov.provincia_cod = fab.provincia_cod
GROUP BY
    prov.provincia_cod,
    fd.producto_cod
ORDER BY
    prov.provincia_cod
    /*Dada el siguiente modelo, desarrolle e implemente un mecanismo que cuando se dé de
     alta un nuevo pase de una jugadora, controle y evite que la jugadora esté fichada para
     dos equipos al mismo tiempo (inclusive al mismo). Es decir, que no permita que dos filas
     en la tabla Pases se superpongan dentro de un periodo para una misma jugadora.*/


CREATE TRIGGER trg_evitar_pases_superpuestos
ON Pases
INSTEAD OF INSERT, UPDATE
AS
BEGIN
/* Esta query funciona para poder observar lo que devuelve en caso de obtener una superposicion, en caso contrario no devuelve nada estaria ok. :)   
SELECT 
        i.Legajo AS legajo_insertado,
        i.FechaDesde AS desde_insertado,
        i.FechaHasta AS hasta_insertado,
        p.FechaDesde AS desde_existente,
        p.FechaHasta AS hasta_existente,
        p.CodigoClub AS club_existente
    FROM INSERTED i
    JOIN Pases p
      ON i.Legajo = p.Legajo
     AND i.FechaDesde <= ISNULL(p.FechaHasta, GETDATE())
     AND p.FechaDesde <= ISNULL(i.FechaHasta, GETDATE());
	 RETURN; */
    -- 1. Chequear superposición con datos existentes
    IF EXISTS (
        SELECT 1
        FROM INSERTED i
        JOIN Pases p
          ON i.Legajo = p.Legajo
         AND i.FechaDesde <= ISNULL(p.FechaHasta, GETDATE())
         AND p.FechaDesde <= ISNULL(i.FechaHasta, GETDATE())
    )
    BEGIN
        RAISERROR('Error: la jugadora tiene un pase que se superpone en las fechas.', 16, 1);
        ROLLBACK TRANSACTION;
        RETURN;
    END;

    -- 2. Eliminar registros anteriores si se trata de un UPDATE
    DELETE p
    FROM Pases p
    JOIN DELETED d
      ON p.Legajo = d.Legajo AND p.FechaDesde = d.FechaDesde;

    -- 3. Insertar nuevos registros (sean de INSERT o UPDATE)
    INSERT INTO Pases (Legajo, FechaDesde, FechaHasta, CodigoClub)
    SELECT Legajo, FechaDesde, FechaHasta, CodigoClub
    FROM INSERTED;
END;
GO